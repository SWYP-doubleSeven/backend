package com.swyp.doubleSeven.domain.member.guest.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GuestLoginRequest {

    private Integer memberId; // 멤버 ID (PK)

    private String memberNickname; // 닉네임 (Guest_UUID)

    private String memberKeyId; // UUID

    private String role; // 권한 (추후 enum 타입으로 변경?)

    private String loginType; // 로그인 타입 (추후 enum 타입으로 변경?)

}
