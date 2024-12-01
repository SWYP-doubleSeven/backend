package com.swyp.doubleSeven.domain.member.guest.service;

import com.swyp.doubleSeven.domain.member.guest.dto.response.GuestLoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public interface GuestService {

    GuestLoginResponse signInGuest(HttpServletRequest request, HttpServletResponse response);

    void deleteExpiredGuestData();
}
