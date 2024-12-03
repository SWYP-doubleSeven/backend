package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.domain.common.enums.LoginType;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.service.GoogleLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class GoogleController {

    private final GoogleLoginService googleLoginService;

    @GetMapping("/google-login")
    public ResponseEntity<MemberResponse> googleLogin(@RequestParam("code") String code, HttpSession session,
                                                      HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {//추후에 리퀘파람 code 는 유연하게 주석 할수도
        googleLoginService.request(LoginType.GOOGLE , response);




    }
}
