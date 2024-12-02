package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class GoogleController {

    @GetMapping("/google-login")
    public ResponseEntity<MemberResponse> googleLogin(@RequestParam("code") String code, HttpSession session, HttpServletRequest httpServletRequest) {//추후에 리퀘파람 code 는 유연하게 주석 할수도


    }
}
