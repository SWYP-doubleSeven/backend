package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/kakao-login")
    public ResponseEntity<String> kakaoLogin(@RequestParam("code") String code, HttpSession session) {
        /* step 1: 프론트에서 다음 url을 통해 호출
        * https://kauth.kakao.com/oauth/authorize?client_id=489a2f33bf9d90c59950291ca077adc9&redirect_uri=http://localhost:8090/api/auth/kakao-login&response_type=code
        * */
        String accessToken = memberService.getKakaoAccessToken(code);
        MemberResponse memberResponse = memberService.processKakaoUser(accessToken);

        session.setAttribute("memberId", memberResponse.getMemberId());
        session.setAttribute("memberNickname", memberResponse.getMemberNickname());
        session.setAttribute("loginType", memberResponse.getLoginType());
        session.setAttribute("role", memberResponse.getRole());

        return ResponseEntity.ok("Kakao login successful");
    }
}
