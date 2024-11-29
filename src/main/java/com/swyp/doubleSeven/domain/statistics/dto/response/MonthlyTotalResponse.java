package com.swyp.doubleSeven.domain.statistics.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "월별 총 절약 금액 응답")
public class MonthlyTotalResponse {

    @Schema(description = "조회 연도", example = "2024")
    private int year;

    @Schema(description = "조회 월", example = "3")
    private int month;

    @Schema(description = "월 총 절약 금액", example = "150000")
    private long monthlyTotal;
}
