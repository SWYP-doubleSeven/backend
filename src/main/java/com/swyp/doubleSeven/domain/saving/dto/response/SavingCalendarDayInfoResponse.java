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
@Schema(description = "일별 가상 소비 정보")
public class SavingCalendarDayInfoResponse {
    @Schema(description = "일", example = "27")
    private int day;

    @Schema(description = "카테고리별 리스트")
    private List<CategorySummary> categorySummaries;

    @Schema(description = "해당 일자 소비 목록")
    private List<SavingResponse> items;
}