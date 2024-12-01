package com.swyp.doubleSeven.domain.member.guest.controller;

import com.swyp.doubleSeven.domain.member.guest.dto.response.GuestLoginResponse;
import com.swyp.doubleSeven.domain.member.guest.service.GuestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
@Tag(name = "", description = "게스트 관련 API")
public class GuestController {

    private final GuestService guestService;

    // 게스트 계정 생성 후 로그인
    /*@PostMapping("/guest")
    public ResponseEntity<GuestLoginResponse> signInGuest (HttpServletRequest request) {
        HttpSession session = request.getSession(true); // 세션이 없다면 새로 생성
        return ResponseEntity.ok(guestService.signInGuest(session));
    }*/

    /*@PostMapping("/guest") public ResponseEntity<GuestLoginResponse> signInGuest (HttpSession session) {
        return ResponseEntity.ok(guestService.signInGuest(session));
    }*/

    @PostMapping ("/guest")
    public ResponseEntity<GuestLoginResponse> signInGuest (
            HttpServletRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok(guestService.signInGuest(request, response));
    }

    @DeleteMapping ("/expired")
    public ResponseEntity<Void> deleteExpiredGuestData () {
        guestService.deleteExpiredGuestData ();
        return ResponseEntity.ok().build();
    }
}