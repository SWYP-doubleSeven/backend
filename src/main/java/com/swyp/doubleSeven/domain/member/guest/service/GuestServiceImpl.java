package com.swyp.doubleSeven.domain.member.guest.service;

import com.swyp.doubleSeven.domain.member.guest.dao.GuestLoginDAO;
import com.swyp.doubleSeven.domain.member.guest.dto.request.ExpiredGuestRequest;
import com.swyp.doubleSeven.domain.member.guest.dto.request.GuestLoginRequest;
import com.swyp.doubleSeven.domain.member.guest.dto.response.GuestLoginResponse;
import com.swyp.doubleSeven.domain.saving.dao.SavingDAO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestServiceImpl implements GuestService{

    private final GuestLoginDAO guestLoginDAO;

    /*@Override
    public GuestLoginResponse signInGuest(HttpSession session) {
        String guestId = UUID.randomUUID().toString();

        GuestLoginRequest member = GuestLoginRequest.builder()
                .memberKeyId("Guest" + guestId)
                .role("GUEST")
                .loginType("GUEST")
                .build();

        guestLoginDAO.signInGuest(member);

        if (session != null) {
            session.setAttribute("memberId", guestId);
            session.setAttribute("role", "GUEST");
            session.setAttribute("loginType", "GUEST");
            session.setMaxInactiveInterval(7* 24* 60* 60);
        }

        return new GuestLoginResponse(member.getMemberId(), member.getMemberKeyId());
    }*/

    @Override public GuestLoginResponse signInGuest(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키에서 memberKeyId 확인
        Cookie[] cookies = request.getCookies();
        String existingMemberKeyId = null;

        // 세션에 memberKeyId가 있다면 해당 멤버 정보 조회
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("memberKeyId".equals(cookie.getName())) {
                    existingMemberKeyId = cookie.getValue();
                    break;
                }
            }
        }

        // 기존 쿠키가 있는 경우
        if (existingMemberKeyId != null) {
            GuestLoginResponse existingMember = guestLoginDAO.selectMemberKeyId(existingMemberKeyId);
            if (existingMember != null) {
                return existingMember;
            }
        }

        // 새로운 게스트 생성
        String memberKeyId = UUID.randomUUID().toString();

        GuestLoginRequest member = GuestLoginRequest.builder()
                .memberKeyId(memberKeyId)
                .memberNickname("Guest_" + memberKeyId.substring(0, 8))
                .role("GUEST")
                .loginType("GUEST")
                .build();

        guestLoginDAO.signInGuest(member);

        // 쿠키 설정
        Cookie guestCookie = new Cookie("memberKeyId", member.getMemberKeyId());
        guestCookie.setMaxAge(7 * 24 * 60 * 60); // 7일
        guestCookie.setPath("/");
        // guestCookie.setHttpOnly(true); // JavaScript에서 접근 방지가 필요한 경우
        // guestCookie.setSecure(true); // HTTPS 필요한 경우

        response.addCookie(guestCookie);

        return new GuestLoginResponse(member.getMemberId(),
                                    member.getMemberKeyId(),
                                    member.getMemberNickname());
    }

    // 쿠키 만료된 게스트 데이터 제거
    @Override
    @Transactional
    public void deleteExpiredGuestData () {
        // 7일이 지난 게스트 데이터 삭제
        // 7일전 구하기
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(7);

        // 만료된 게스트 목록 조회
        List<ExpiredGuestRequest> expiredGuests = guestLoginDAO.selectExpiredGuestIds (expirationDate);

        if (!expiredGuests.isEmpty()) {
            // id 목록 추출
            List<Integer> memberIds = expiredGuests.stream()
                    .map(ExpiredGuestRequest::getMemberId)
                    .collect(Collectors.toList());

            // Saving 테이블 데이터 삭제
            guestLoginDAO.deleteSavingsByGuestIds (memberIds);

            // Member 테이블 데이터 삭제
            guestLoginDAO.deleteExpiredGuests (expirationDate);
        }
    }
}
