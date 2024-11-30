package com.swyp.doubleSeven.domain.member.kakao.controller;

import com.swyp.doubleSeven.domain.member.kakao.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/kakao-login")
    public ResponseEntity<String> kakaoLogin(@RequestParam("code") String code) {
        /* step 1: 프론트에서 다음 url을 통해 호출
        * https://kauth.kakao.com/oauth/authorize?client_id=489a2f33bf9d90c59950291ca077adc9&redirect_uri=http://localhost:8090/api/auth/kakao-login&response_type=code
        * */
        String accessToken = memberService.getKakaoAccessToken(code);
        memberService.processKakaoUser(accessToken);
        return ResponseEntity.ok("Kakao login successful");
    }
}
