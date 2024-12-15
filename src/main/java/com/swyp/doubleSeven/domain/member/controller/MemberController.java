package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.common.aspect.AuthenticationUtil;
import com.swyp.doubleSeven.common.aspect.anotation.AuthCheck;
import com.swyp.doubleSeven.common.exception.BusinessException;
import com.swyp.doubleSeven.common.util.CommonAspect;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.common.enums.Error;
import com.swyp.doubleSeven.domain.common.enums.Role;
import com.swyp.doubleSeven.domain.member.dto.request.MemberRequest;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final CommonAspect commonAspect;
    private final AuthenticationUtil authenticationUtil;

    @GetMapping("/auth/kakao-login")
    //@CrossOrigin(origins = {"https://zerocost-eta.vercel.app", "http://localhost:3000", "https://zerocost.swygbro.com"}, allowCredentials = "true")
    public ResponseEntity<MemberResponse> kakaoLogin(
            @RequestParam("code") String memberKeyId
            , HttpServletResponse response
            , HttpServletRequest request) {

        log.info("Request received from: {}", request.getHeader("Origin"));
        log.info("Request URL: {}", request.getRequestURL());
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Server Name: {}", request.getServerName());
        log.info("Server Port: {}", request.getServerPort());

        MemberResponse memberResponse = memberService.processKakaoUser(memberKeyId);

        List<BadgeResponse> badgeResponseList = commonAspect.afterLogin(memberResponse.getMemberId().intValue());
        memberResponse.setBadgeResponseList(badgeResponseList);

        // 쿠키 설정을 위한 공통 속성 (Domain 유지)
//        String cookieProperties = "Path=/; SameSite=None; Secure; HttpOnly; Max-Age=2592000; Domain=api-zerocost.site";
//        // 각 쿠키 설정
//        response.setHeader("Set-Cookie", String.format("memberKeyId=%s; %s",
//                memberResponse.getMemberKeyId(), cookieProperties));
//        response.setHeader("Set-Cookie", String.format("memberId=%s; %s",
//                memberResponse.getMemberId().toString(), cookieProperties));
//        response.setHeader("Set-Cookie", String.format("loginType=%s; %s",
//                "KAKAO", cookieProperties));
//
//        log.info("====카카오 로그인=====");
//        log.info("쿠키 속성: {}", cookieProperties);
//        log.info("Set-Cookie: memberKeyId={}", memberResponse.getMemberKeyId());
//        log.info("Set-Cookie: memberId={}", memberResponse.getMemberId());

        setCookie(response, "memberKeyId", memberKeyId);
        setCookie(response, "memberId", String.valueOf(memberResponse.getMemberId()));
        setCookie(response, "loginType", memberResponse.getLoginType());


        return ResponseEntity.ok(memberResponse);
    }

    private void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);  // 쿠키 생성
        cookie.setMaxAge(2592000);                 // 유효기간 설정 (초 단위)
        cookie.setPath("/");                      // 모든 경로에서 접근 가능
        cookie.setSecure(true);                   // HTTPS 전용
        cookie.setHttpOnly(true);                 // JavaScript 접근 차단
        cookie.setDomain("api-zerocost.site");    // 쿠키 도메인 설정
        response.addCookie(cookie);               // 응답에 쿠키 추가
    }


    @PutMapping("/members/{memberId}/nickname")
    @Operation(summary = "멤버 닉네임 업데이트", description = "사용자가 멤버 닉네임을 업데이트합니다")
//    @AuthCheck(allowedRoles = Role.MEMBER)
    public ResponseEntity<MemberResponse> updateMemberNickname(
            @PathVariable("memberId") Integer memberId
            , @RequestParam("memberNickname") String memberNickname) {
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setMemberId(memberId);
        memberRequest.setMemberNickname(memberNickname);
        MemberResponse memberResponse = memberService.updateMemberNickname(memberRequest);
        return ResponseEntity.ok().body(memberResponse);
    }

    @PostMapping("/auth/kakao-withdrawal")
//    @AuthCheck(allowedRoles = Role.MEMBER)
    @Operation(summary = "회원 탈퇴", description = "사용자가 회원탈퇴합니다")
    public int withdrawMember(@RequestParam("memberId") Integer currentMemberId) {
//        Integer currentMemberId = authenticationUtil.getCurrentMemberId();
//        if(currentMemberId != memberId) {
//            throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
//        }
        return memberService.withdrawMember(currentMemberId);
    }

    @PostMapping("/auth/logout")
    @Operation(summary = "로그아웃", description = "사용자가 로그아웃 합니다")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 쿠키 이름들
        String[] cookiesToDelete = {"memberKeyId", "memberId", "loginType"};

        for (String cookieName : cookiesToDelete) {
            Cookie cookie = new Cookie(cookieName, null);
            cookie.setMaxAge(0); // 쿠키 만료
            cookie.setPath("/"); // Path 일치
            cookie.setDomain("api-zerocost.site"); // Domain 일치
            cookie.setSecure(true); // HTTPS 사용
            cookie.setHttpOnly(true); // JS 접근 차단
            response.addCookie(cookie);
        }

        return ResponseEntity.ok("로그아웃 성공");
    }
}
