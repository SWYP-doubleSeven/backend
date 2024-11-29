package com.swyp.doubleSeven.domain.saving.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "캘린더뷰")
public class SavingCalendarResponse {
    @Schema(description = "연도", example = "2024")
    private int year;

    @Schema(description = "월", example = "3")
    private int month;

    @Schema(description = "일별 소비 정보")
    private List<SavingCalendarDayInfoResponse> days;
}