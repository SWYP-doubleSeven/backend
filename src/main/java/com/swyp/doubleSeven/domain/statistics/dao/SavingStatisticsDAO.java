package com.swyp.doubleSeven.domain.statistics.dao;

import com.swyp.doubleSeven.domain.statistics.dto.response.CategoryStatisticsResponse;
import com.swyp.doubleSeven.domain.statistics.dto.response.HourlyStatisticsResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SavingStatisticsDAO {

    // 월별 총액 조회
    long selectMonthlyTotal(int year, int month);

    // 카테고리별 통계 조회
    List<CategoryStatisticsResponse> selectCategoryStatistics(int year, int month);

    List<HourlyStatisticsResponse> selectHourlyStatistics(int year, int month);
}
