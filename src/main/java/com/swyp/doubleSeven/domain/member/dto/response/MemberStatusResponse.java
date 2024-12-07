package com.swyp.doubleSeven.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "[ 제로코스트와 함께 한 N일 동안 N원을 절약했어요. ] 해당 문구에 응답합니다.")
public class MemberStatusResponse {

    @Schema(description = "가입일로부터 경과일수", example = "5")
    private int daysFromRegistration;

    @Schema(description = "가입일로부터 총 절약 금액", example = "18000")
    private int totalAmount;
}
