package com.swyp.doubleSeven.domain.member.dao;

import com.swyp.doubleSeven.domain.member.dto.request.guest.ExpiredGuestRequest;
import com.swyp.doubleSeven.domain.member.dto.request.guest.GuestLoginRequest;
import com.swyp.doubleSeven.domain.member.dto.response.guest.GuestLoginResponse;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GuestDAO {
    // 게스트로 로그인/재로그인
    void signInGuest(GuestLoginRequest guestLoginRequest);

    // 재로그인인지 확인을 위한 memberKeyId 조회
    GuestLoginResponse selectMemberKeyId(String memberKeyId);

    // 쿠키 만료 게스트 ID 조회
    List<ExpiredGuestRequest> selectExpiredGuestIds(LocalDateTime expirationDate);

    // 쿠키 만료된 게스트 데이터 삭제 (Saving Table)
    void deleteSavingsByGuestIds (List<Integer> guestIds);

    // 쿠키 만료된 게스트 데이터 삭데 (Member Table)
    void deleteExpiredGuests (LocalDateTime expirationDate);

    // 소셜 로그인 연동 유도를 위한 counting
    int countSaving (Integer memberId);
}
