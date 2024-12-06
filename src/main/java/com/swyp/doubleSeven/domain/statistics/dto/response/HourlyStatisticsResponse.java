package com.swyp.doubleSeven.domain.statistics.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
@Schema(description = "시간대별 절약 통계 응답")
public class HourlyStatisticsResponse  {

    @Schema(description = "시간대(0-23)", example = "14")
    private int hour;

    @Schema(description = "해당 시간대 절약 총액", example = "25000")
    private long amount;

    @Schema(description = "해당 시간대 절약 건수", example = "5")
    private int count;
}
