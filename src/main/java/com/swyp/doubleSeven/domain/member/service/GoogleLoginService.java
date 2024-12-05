package com.swyp.doubleSeven.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swyp.doubleSeven.domain.common.enums.LoginType;
import com.swyp.doubleSeven.domain.member.dao.MemberDAO;
import com.swyp.doubleSeven.domain.member.dto.request.MemberRequest;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleLoginService {

    private final GoogleOauth googleOauth;
    private final MemberDAO memberRepo;
    private final HttpServletResponse response;

    public void request(LoginType socialLoginType) throws IOException {
        //매개변수의 Resposne 그냥 필드에 박하 넣을수도 있음


        String redirectURL;
        redirectURL = googleOauth.getOauthRedirectUrl();
//            }break;
//            default:{
//                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
//            }

        response.sendRedirect(redirectURL);
    }

    public void oauthLogin(LoginType loginType, String code) throws IOException {
        if (loginType == LoginType.GOOGLE) {
            try {
                // 액세스 토큰 요청
                String accessToken = googleOauth.requestAccessToken(code);
                // 사용자 정보 가져오기
                Map<String, Object> userInfo = googleOauth.getUserInfo(accessToken);
                // 사용자 정보 처리 (회원가입 또는 로그인)
                handleUserInfo(userInfo);

                log.info("그럼 여기?");
                // 로그인 성공 후 리다이렉트 또는 토큰 발급 등
                response.sendRedirect("/home"); // 예시로 홈 페이지로 리다이렉트
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException("구글 로그인 처리 중 오류 발생");
            }
        } else {
            throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
        }


    }


    private void handleUserInfo(Map<String, Object> userInfo) {
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        log.info("의심");
        String memberKeyId = (String) userInfo.get("id"); // OAuth 제공자의 사용자 고유 ID
        String loginType = "GOOGLE"; // 로그인 유형 설정 (예: "GOOGLE")

        log.info("의심1");

        // 데이터베이스에서 사용자 조회
        MemberResponse existingMember = memberRepo.findMemberByOauthProviderAndMemberId(loginType, Long.valueOf(memberKeyId));
        log.info("is it here?");
        // MemberRequest 빌더를 사용하여 회원 정보 생성
        MemberRequest.MemberRequestBuilder memberRequestBuilder = MemberRequest.builder()
                .memberKeyId(memberKeyId)
                .loginType(loginType)
                .memberNickname(name)
                .email(email)
                .role("ROLE_USER") // 권한 설정
                .dltnYn("N")
                .rgstId(0L)
                .rgstDt(LocalDateTime.now())
                .updtId(0L)
                .updtDt(LocalDateTime.now());

        log.debug("회원 처리 시작");

        if (existingMember == null) {
            // 신규 회원 가입 처리
            memberRepo.insertMember(memberRequestBuilder.build());
            log.debug("신규 회원 가입 완료");
        } else {
            // 기존 회원 로그인 처리
            // 필요에 따라 회원 정보 업데이트 로직 추가
            log.debug("기존 회원 로그인 처리");
        }

        //return;

        // 세션 또는 토큰을 통해 로그인 상태 유지
        // 예를 들어, 세션에 사용자 정보 저장
        // HttpSession session = request.getSession();
        // session.setAttribute("member", existingMember != null ? existingMember : memberRequestBuilder.build());
//        String email = (String) userInfo.get("email");
//        String name = (String) userInfo.get("name");
//
//        // 데이터베이스에서 사용자 조회
//        //Member member = null;//memberMapper.selectMemberByEmail(email);
//        MemberResponse memberByOauthProviderAndMemberId = memberRepo.findMemberByOauthProviderAndMemberId();
//
//        if (member == null) {
//            // 신규 회원 가입
//            member = new Member();
//            member.setEmail(email);
//            member.setMemberName(name);
//            member.setMemberNickname(name);
//            member.setRole("ROLE_USER");
//            member.setOauthProvider("G"); // 구글의 경우 'G'
//            member.setDltnYn("N");
//            member.setRgstId(0); // 시스템 관리자 ID로 설정하거나 적절한 값으로 설정
//            member.setUpdtId(0);
//
//            //memberMapper.insertMember(member); 멤버 리포로 수정
//            memberRepo.insertMember(member);
//
//        } else {
//            // 기존 회원 로그인 처리
//            // 필요에 따라 업데이트 로직 추가
//        }
//
//        // 세션 또는 토큰을 통해 로그인 상태 유지
//        // 예를 들어, 세션에 사용자 정보 저장
//        // HttpSession session = request.getSession();
//        // session.setAttribute("member", member);
    }


}
