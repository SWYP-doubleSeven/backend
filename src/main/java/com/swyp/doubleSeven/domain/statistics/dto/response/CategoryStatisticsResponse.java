package com.swyp.doubleSeven.domain.statistics.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "카테고리별 절약 통계 응답")
public class CategoryStatisticsResponse  {

    @Schema(description = "카테고리명", example = "식비")
    private String categoryName;

    @Schema(description = "해당 카테고리 절약 총액", example = "45000")
    private long amount;

    @Schema(description = "전체 대비 해당 카테고리 비율", example = "30.5")
    private double percentage;
}
