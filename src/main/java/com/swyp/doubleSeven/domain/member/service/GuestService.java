package com.swyp.doubleSeven.domain.member.service;

import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.dto.response.guest.GuestLoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface GuestService {
    GuestLoginResponse signInGuest(HttpServletRequest request, HttpServletResponse response);

    void deleteExpiredGuestData();

    // 소셜 로그인 연동 유도를 위한 counting
    boolean countSaving(Integer memberId);
}
