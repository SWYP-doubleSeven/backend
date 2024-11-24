package com.swyp.doubleSeven.domain.statistics.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryStatisticsResponse  {

    private String categoryName;

    private long amount;

    private double percentage;
}
