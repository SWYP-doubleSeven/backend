package com.swyp.doubleSeven.domain.member.service;

import com.swyp.doubleSeven.domain.member.dao.GuestDAO;
import com.swyp.doubleSeven.domain.member.dto.reqeust.guest.ExpiredGuestRequest;
import com.swyp.doubleSeven.domain.member.dto.reqeust.guest.GuestLoginRequest;
import com.swyp.doubleSeven.domain.member.dto.response.guest.GuestLoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService{

    private final GuestDAO guestDAO;

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
            GuestLoginResponse existingMember = guestDAO.selectMemberKeyId(existingMemberKeyId);
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

        guestDAO.signInGuest(member);

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
        List<ExpiredGuestRequest> expiredGuests = guestDAO.selectExpiredGuestIds (expirationDate);

        if (!expiredGuests.isEmpty()) {
            // id 목록 추출
            List<Integer> memberIds = expiredGuests.stream()
                    .map(ExpiredGuestRequest::getMemberId)
                    .collect(Collectors.toList());

            // Saving 테이블 데이터 삭제
            guestDAO.deleteSavingsByGuestIds (memberIds);

            // Member 테이블 데이터 삭제
            guestDAO.deleteExpiredGuests (expirationDate);
        }
    }
}
