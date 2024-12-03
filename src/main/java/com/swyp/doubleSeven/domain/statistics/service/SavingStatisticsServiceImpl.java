package com.swyp.doubleSeven.domain.statistics.service;

import com.swyp.doubleSeven.domain.statistics.dao.SavingStatisticsDAO;
import com.swyp.doubleSeven.domain.statistics.dto.response.CategoryStatisticsResponse;
import com.swyp.doubleSeven.domain.statistics.dto.response.HourlyStatisticsResponse;
import com.swyp.doubleSeven.domain.statistics.dto.response.MonthlyTotalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingStatisticsServiceImpl implements SavingStatisticsService {

    private final SavingStatisticsDAO statisticsDAO;

    @Override
    public MonthlyTotalResponse getMonthlyTotal(int year, int month, Integer memberId) {
        long total = statisticsDAO.selectMonthlyTotal(year, month, memberId);
        return MonthlyTotalResponse.builder()
                .year(year)
                .month(month)
                .monthlyTotal(total)
                .build();
    }

    @Override
    public List<CategoryStatisticsResponse> getCategoryStatistics(int year, int month, Integer memberId) {
        return statisticsDAO.selectCategoryStatistics(year, month, memberId);
    }

    @Override
    public List<HourlyStatisticsResponse> getHourlyStatistics(int year, int month, Integer memberId) {
        return statisticsDAO.selectHourlyStatistics(year, month, memberId);
    }

}
