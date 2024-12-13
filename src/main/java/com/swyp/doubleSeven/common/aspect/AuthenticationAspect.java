package com.swyp.doubleSeven.common.aspect;

import com.swyp.doubleSeven.common.aspect.anotation.AuthCheck;
import com.swyp.doubleSeven.common.exception.BusinessException;
import com.swyp.doubleSeven.domain.common.enums.Error;
import com.swyp.doubleSeven.domain.common.enums.LoginType;
import com.swyp.doubleSeven.domain.common.enums.Role;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.error.GuestError;
import com.swyp.doubleSeven.domain.member.dao.GuestDAO;
import com.swyp.doubleSeven.domain.member.dao.MemberDAO;
import com.swyp.doubleSeven.domain.member.dto.response.guest.GuestLoginResponse;
import com.swyp.doubleSeven.domain.member.error.MemberError;
import com.swyp.doubleSeven.domain.saving.dao.SavingDAO;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationAspect {

    private final HttpServletRequest request;

    private final MemberDAO memberDAO;

    private final SavingDAO savingDAO;

    private final GuestDAO guestDAO;

    /**
     * AuthCheck 어노테이션이 붙은 메서드 실행 전에 권한을 검증합니다.
     *
     * @param joinPoint AOP 조인포인트
     * @param authCheck AuthCheck 어노테이션 객체 ex) @Before("@annotation(authCheck) && args(savingRequest,..)")
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object checkAuth (ProceedingJoinPoint joinPoint, AuthCheck authCheck) {
        try {
            // 1. 인증 정보 조회
            AuthInfo authInfo = getAuthInfo();
            log.info("Retrieved authInfo: {}", authInfo);

            // 2. 권한 검증
            validateAuth(authInfo, authCheck);

            // 3. Request 객체에 memberId 설정
            Object[] args = joinPoint.getArgs();
            if (args != null) {
                setMemberIdForArgs(args, authInfo.getMemberId());
            }

            // 4. 작성자 권한 검증 (필요한 경우)
            if (authCheck.validateAuthor()) {
                validateAuthorByResource(joinPoint, authInfo.getMemberId());
            }

            // 5. 메서드 실행
            return joinPoint.proceed();

        } catch (BusinessException e) {
            log.error("Business exception occurred: {}", e.getMessage());
            throw e;
        } catch (Throwable e) {
            log.error("Authentication check failed: {}", e.getMessage());
            throw new BusinessException(Error.INTERVAL_SERVER_ERROR);
        }
    }

    /**
     * 쿠키에서 사용자 인증 정보를 조회합니다.
     * @return AuthInfo 사용자 인증 정보
     * @throws //BusinessException LOGIN_REQUIRED (로그인 필요시)
     */
    private AuthInfo getAuthInfo () {

        // 로그인 - 쿠키 확인
        String memberKeyId = extractMemberKeyIdFromCookies();
        if (memberKeyId != null) {

            // 1. 멤버 로그인 정보 확인
            MemberResponse memberInfo = memberDAO.findMemberByMemberKeyId(memberKeyId);
            if(memberInfo != null) {
                return AuthInfo.builder()
                        .memberId(memberInfo.getMemberId())
                        .loginType(LoginType.KAKAO.name())
                        .role(Role.MEMBER.name())
                        .build();

                // 2. 게스트 로그인 정보 확인
            } else {
                GuestLoginResponse guestInfo = guestDAO.selectMemberKeyId(memberKeyId);
                log.debug("Guest info from DB: {}", guestInfo);

                if (guestInfo == null) {
                    throw new BusinessException(GuestError.NOTFOUND_GUEST);
                }

                return AuthInfo.builder()
                        .memberId(guestInfo.getMemberId())
                        .loginType(LoginType.GUEST.name())
                        .role(Role.GUEST.name())
                        .build();
            }

        }

        // 3. 미인증 사용자
        log.debug("No authentication info found");

        throw new BusinessException(Error.LOGIN_REQUIRED);

    }

    /**
     * 쿠키에서 memberKeyId를 추출합니다.
     */
    private String extractMemberKeyIdFromCookies() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("memberKeyId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 쿠키에서 memberId를 추출합니다.
     */
    private String extractMemberIdFromCookies() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("memberId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    /**
     * 사용자의 권한을 검증합니다.
     * @param authInfo 사용자 인증 정보
     * @param authCheck 권한 검증 설정
     * @throws BusinessException 권한 없음 예외
     */
    private void validateAuth(AuthInfo authInfo, AuthCheck authCheck) {
        log.info("Validating auth for user with loginType: {} and role: {}",
                authInfo.getLoginType(), authInfo.getRole());


        // 1. 로그인 타입 검증
        LoginType loginType = LoginType.valueOf(authInfo.getLoginType());
        if (!Arrays.asList(authCheck.allowedTypes()).contains(loginType)) {
            throw new BusinessException(loginType == LoginType.GUEST
                    ? GuestError.GUEST_ACCESS_DENIED
                    : MemberError.LOGINTYPE_ACCESS_DENIED);
        }

        // 2. 역할 검증
        Role userRole = Role.valueOf(authInfo.getRole());
        if (!Arrays.asList(authCheck.allowedRoles()).contains(userRole)) {
            throw new BusinessException(userRole == Role.GUEST
                    ? GuestError.GUEST_ACCESS_DENIED
                    : MemberError.MEMBER_ACCESS_DENIED);
        }
        log.info("Auth validation successful");

    }

    /**
     * DTO 객체에 memberId를 설정합니다.
     * @param args 메서드 파라미터 배열
     * @param memberId 설정할 memberId
     */
    private void setMemberIdForArgs(Object[] args, Integer memberId) {
        Arrays.stream(args)
                .filter(arg -> arg != null && arg.getClass().getSimpleName().endsWith("Request"))
                .forEach(arg -> {
                    try {
                        Method setMemberId = arg.getClass().getMethod("setMemberId", Integer.class);
                        setMemberId.invoke(arg, memberId);



                        Method getMemberId = arg.getClass().getMethod("getMemberId");
                        Integer setMemberIdValue = (Integer) getMemberId.invoke(arg);
                        log.info("==========setMemberIdForArgs========");
                        log.info("Set and verified memberId {} for {}", setMemberIdValue, arg.getClass().getSimpleName());
                    } catch (Exception e) {
                        throw new BusinessException(Error.BAD_REQUEST);
                    }
                });
    }

    /**
     * saving CRUD시 작성자 권한을 검증합니다.
     * @param joinPoint AOP 조인포인트
     * @param currentMemberId 현재 사용자 ID
     * @throws BusinessException 권한 없음 예외
     */
    private void validateAuthorByResource(JoinPoint joinPoint, Integer currentMemberId) {
        log.debug("Validating author rights for memberId: {}", currentMemberId);

        // 1. PathVariable에서 resourceId 추출
        Integer savingId = extractSavingId(joinPoint);
        if (savingId == null) {
            throw new BusinessException(Error.BAD_REQUEST);
        }
        // 1. 메서드의 모든 파라미터를 가져옴
        Object[] args = joinPoint.getArgs();


        // 2. DB에서 saving 조회하여 작성자 확인
        SavingResponse saving = savingDAO.selectSaving(savingId, currentMemberId);
        if (saving == null) {
            throw new BusinessException(Error.BAD_REQUEST);
        }

        // 3. 작성자 권한 검증
        if (!currentMemberId.equals(saving.getMemberId())) {
            log.warn("접근제한됨. 현재유저: {}, 글작성자: {}", currentMemberId, saving.getMemberId());
            throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
        }



        log.debug("성공 savingId: {}", savingId);
        /*// 2. 각 파라미터를 순회
        for (Object arg : args) {

            if (arg == null) continue;  // null이면 다음 파라미터로

            String className = arg.getClass().getSimpleName();
            log.debug("Checking author validation for argument type: {}", className);

            try {
                Method getMemberId = arg.getClass().getMethod("getMemberId");
                Integer resourceMemberId = (Integer) getMemberId.invoke(arg);

                log.debug("Comparing currentMemberId: {} with resourceMemberId: {}",
                        currentMemberId, resourceMemberId);

                if (resourceMemberId != null && !currentMemberId.equals(resourceMemberId)) {
                    throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
                }
            } catch (NoSuchMethodException e) {
                log.debug("No getMemberId method in {}", className);
            } catch (Exception e) {
                log.warn("Failed to validate author for {}: {}", className, e.getMessage());
            }

            *//*try {
                Method getMemberId = arg.getClass().getMethod("getMemberId");
                Integer resourceMemberId = (Integer) getMemberId.invoke(arg);

                // 작성자 ID 비교 및 권한 검증
                if (resourceMemberId != null && !currentMemberId.equals(resourceMemberId)) {
                    throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
                }
            } catch (Exception e) {
                // 기타 예외 발생시 로그만 남기고
                // for문의 다음 파라미터로 진행
                log.warn("Failed to validate author for {}: {}",
                        arg.getClass().getSimpleName(), e.getMessage());
            }*//*
        }*/
    }



    private Integer extractSavingId (JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        // PathVariable로 전달된 Integer 타입의 savingId 찾기
        for (Object arg : args) {
            if (arg instanceof Integer) {
                return (Integer) arg;
            }
        }

        // savingId를 찾지 못한 경우
        log.warn("No savingId found in method arguments");
        return null;
    }

    /*// 소셜 로그인 권한 검증
    private void validateSosialUser (String loginType, String role, AuthCheck authCheck) {

        // 로그인 타입 검증
        if (!Arrays.asList(authCheck.allowedTypes()).contains(LoginType.valueOf(loginType))) {
            throw new BusinessException(MemberError.LOGINTYPE_ACCESS_DENIED);
        }

        // 역할 검증
        if (!Arrays.asList(authCheck.allowedRoles()).contains(Role.valueOf(role))) {
            throw new BusinessException(MemberError.MEMBER_ACCESS_DENIED);
        }
    }




    // 게스트 로그인 권한 검증
    private void validateGuestUser (String memberKeyId, AuthCheck authCheck) {

        GuestLoginResponse guestInfo = guestDAO.selectMemberKeyId(memberKeyId);

        // 게스트 로그인 검증
        if (guestInfo == null) {
            throw new BusinessException(GuestError.NOTFOUND_GUEST);
        }

        // 게스트 권한 검증
        if (!Arrays.asList(authCheck.allowedTypes()).contains(LoginType.GUEST)) {
            throw new BusinessException(GuestError.GUEST_ACCESS_DENIED);
        }
    }*/

    /*private void validateAuthor (JoinPoint joinPoint, Integer currentMemberId) {

        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof SavingRequest) {
                Integer resourceMemberId = ((SavingRequest) arg).getMemberId();

                if (!currentMemberId.equals(resourceMemberId)) {
                    throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
                }
                return;
            }
        }
    }*/
}
