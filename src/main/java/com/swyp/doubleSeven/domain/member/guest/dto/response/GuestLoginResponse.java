package com.swyp.doubleSeven.domain.member.guest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestLoginResponse {

    private Integer memberId; // 멤버 ID (PK)

    private String memberKeyId; // UUID

    private String memberNickname; // Guest_UUID
}
