package com.swyp.doubleSeven.common.aspect;

import com.swyp.doubleSeven.common.exception.BusinessException;
import com.swyp.doubleSeven.domain.common.enums.Error;
import com.swyp.doubleSeven.domain.common.enums.LoginType;
import com.swyp.doubleSeven.domain.member.dao.GuestDAO;
import com.swyp.doubleSeven.domain.member.dto.response.guest.GuestLoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationUtil {
    private final HttpServletRequest request;
    private final GuestDAO guestDAO;

    public Integer getCurrentMemberId() {
        // 1. 세션에서 로그인 타입 확인
        String loginTypeStr = (String) request.getSession().getAttribute("loginType");

        // 소셜 로그인 (KAKAO, GOOGLE) 체크
        if (loginTypeStr != null) {
            LoginType loginType = LoginType.valueOf(loginTypeStr);
            if (loginType == LoginType.KAKAO || loginType == LoginType.GOOGLE) {
                return (Integer) request.getSession().getAttribute("memberId");
            }
        }

        // 2. 게스트 로그인 체크
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("memberKeyId".equals(cookie.getName())) {
                    GuestLoginResponse guestInfo = guestDAO.selectMemberKeyId(cookie.getValue());
                    if (guestInfo != null) {
                        return guestInfo.getMemberId();
                    }
                }
            }
        }

        return null;
    }
}
