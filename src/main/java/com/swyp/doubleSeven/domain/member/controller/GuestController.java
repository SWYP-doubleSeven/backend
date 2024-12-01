package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.domain.member.dto.response.guest.GuestLoginResponse;
import com.swyp.doubleSeven.domain.member.service.GuestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping("/singin")
    public ResponseEntity<GuestLoginResponse> signInGuest (
            HttpServletRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok(guestService.signInGuest(request, response));
    }

    @DeleteMapping("/expired")
    public ResponseEntity<Void> deleteExpiredGuestData () {
        guestService.deleteExpiredGuestData ();
        return ResponseEntity.ok().build();
    }
}
