package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.common.util.CommonAspect;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final CommonAspect commonAspect;

    @GetMapping("/kakao-login")
    public ResponseEntity<MemberResponse> kakaoLogin(@RequestParam("code") String code, HttpSession session) {
        /* step 1: 프론트에서 다음 url을 통해 호출
        * https://kauth.kakao.com/oauth/authorize?client_id=489a2f33bf9d90c59950291ca077adc9&redirect_uri=http://localhost:8090/api/auth/kakao-login&response_type=code
        * */
        // 로그 추가
        log.info("Received Authorization Code: {}", code);
        log.info("Redirect URI Expected: http://3.39.123.15:8090/api/auth/kakao-login");


        String accessToken = memberService.getKakaoAccessToken(code);
        MemberResponse memberResponse = memberService.processKakaoUser(accessToken);

        session.setAttribute("memberId", memberResponse.getMemberId());
        session.setAttribute("memberNickname", memberResponse.getMemberNickname());
        session.setAttribute("loginType", memberResponse.getLoginType());
        session.setAttribute("role", memberResponse.getRole());

        List<BadgeResponse> badgeResponseList = commonAspect.afterLogin(memberResponse.getMemberId().intValue());
        memberResponse.setBadgeResponseList(badgeResponseList);

        return ResponseEntity.ok(memberResponse);
    }
}
