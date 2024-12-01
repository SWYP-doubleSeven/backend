package com.swyp.doubleSeven.domain.member.guest.dao;

import com.swyp.doubleSeven.domain.member.guest.dto.request.ExpiredGuestRequest;
import com.swyp.doubleSeven.domain.member.guest.dto.request.GuestLoginRequest;
import com.swyp.doubleSeven.domain.member.guest.dto.response.GuestLoginResponse;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface GuestLoginDAO {
    void signInGuest(GuestLoginRequest guestLoginRequest);

    GuestLoginResponse selectMemberKeyId(String memberKeyId);

    // 쿠키 만료 게스트 ID 조회
    List<ExpiredGuestRequest> selectExpiredGuestIds(LocalDateTime expirationDate);

    // 쿠키 만료된 게스트 데이터 삭제 (Saving Table)
    void deleteSavingsByGuestIds (List<Integer> guestIds);

    // 쿠키 만료된 게스트 데이터 삭데 (Member Table)
    void deleteExpiredGuests (LocalDateTime expirationDate);
}
