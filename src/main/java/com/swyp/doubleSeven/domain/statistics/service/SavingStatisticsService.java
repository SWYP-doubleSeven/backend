package com.swyp.doubleSeven.domain.statistics.service;

import com.swyp.doubleSeven.domain.statistics.dto.response.CategoryStatisticsResponse;
import com.swyp.doubleSeven.domain.statistics.dto.response.HourlyStatisticsResponse;
import com.swyp.doubleSeven.domain.statistics.dto.response.MonthlyTotalResponse;

import java.util.List;

public interface SavingStatisticsService {

    MonthlyTotalResponse getMonthlyTotal(int year, int month, Integer memberId);

    List<CategoryStatisticsResponse> getCategoryStatistics(int year, int month, Integer memberId);

    List<HourlyStatisticsResponse> getHourlyStatistics(int year, int month, Integer memberId);
}
