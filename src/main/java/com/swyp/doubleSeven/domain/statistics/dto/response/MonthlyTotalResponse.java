package com.swyp.doubleSeven.domain.statistics.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MonthlyTotalResponse {

    private int year;

    private int month;

    private long monthlyTotal;

    //private List<CategoryStatisticsResponse> categoryStatistics;

    //private List<HourlyStatisticsResponse> hourlyStatistics;
}
