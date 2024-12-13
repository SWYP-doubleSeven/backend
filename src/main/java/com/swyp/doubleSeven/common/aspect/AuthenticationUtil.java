package com.swyp.doubleSeven.common.aspect;

import com.swyp.doubleSeven.domain.member.dao.GuestDAO;
import com.swyp.doubleSeven.domain.member.dao.MemberDAO;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.dto.response.guest.GuestLoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationUtil {
    private final HttpServletRequest request;
    private final GuestDAO guestDAO;
    private final MemberDAO memberDAO;

    public Integer getCurrentMemberId() {

        Cookie[] cookies = request.getCookies();
        String memberKeyId = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("memberKeyId".equals(cookie.getName())) {
                    memberKeyId = cookie.getValue();
                    break;
                }
            }
        }
        log.info("getCurrentMemberId: {}", memberKeyId);

        MemberResponse memberInfo = memberDAO.findMemberByMemberKeyId(memberKeyId);
        if(memberInfo != null) {
            log.info("getCurrentMemberId의 getMemberId: {}", memberInfo.getMemberId());
            return memberInfo.getMemberId();
        }

        GuestLoginResponse guestInfo = guestDAO.selectMemberKeyId(memberKeyId);
        if (guestInfo != null) {
            return guestInfo.getMemberId();
        }


        return null;
    }
}
