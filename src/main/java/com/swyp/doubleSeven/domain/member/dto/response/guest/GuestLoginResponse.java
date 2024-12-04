package com.swyp.doubleSeven.domain.member.dto.response.guest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게스트 로그인 응답")
public class GuestLoginResponse {

    @Schema(description = "멤버 ID", example = "1")
    private Integer memberId; // 멤버 ID (PK)

    @Schema(description = "게스트 고유 ID", example = "8c43d7f9-ce4b-4f1d-8164-5a62d5e4f5dc")
    private String memberKeyId; // UUID

    @Schema(description = "게스트 닉네임", example = "Guest_8c43d7f9")
    private String memberNickname; // Guest_UUID
}
