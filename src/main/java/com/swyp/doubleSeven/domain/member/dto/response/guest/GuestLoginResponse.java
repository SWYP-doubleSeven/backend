package com.swyp.doubleSeven.domain.member.dto.response.guest;

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
