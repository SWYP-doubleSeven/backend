package com.swyp.doubleSeven.domain.statistics.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HourlyStatisticsResponse  {

    private int hour;

    private long amount;

    private int count;
}
