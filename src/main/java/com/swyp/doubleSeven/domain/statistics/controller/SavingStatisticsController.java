package com.swyp.doubleSeven.domain.statistics.controller;

import com.swyp.doubleSeven.domain.statistics.dto.response.CategoryStatisticsResponse;
import com.swyp.doubleSeven.domain.statistics.dto.response.HourlyStatisticsResponse;
import com.swyp.doubleSeven.domain.statistics.dto.response.MonthlyTotalResponse;
import com.swyp.doubleSeven.domain.statistics.service.SavingStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/statistics")
public class SavingStatisticsController {

    private final SavingStatisticsService statisticsService;

    // 월별 총 절약 금액
    @GetMapping("/monthly-total/{year}/{month}")
    public ResponseEntity<MonthlyTotalResponse> getMonthlyTotal(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(statisticsService.getMonthlyTotal(year, month));
    }

    // 카테고리별 통계
    @GetMapping("/category/{year}/{month}")
    public ResponseEntity<List<CategoryStatisticsResponse>> getCategoryStatistics(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(statisticsService.getCategoryStatistics(year, month));
    }

    // 시간대별 통계
    @GetMapping("/hourly/{year}/{month}")
    public ResponseEntity<List<HourlyStatisticsResponse>> getHourlyStatistics(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(statisticsService.getHourlyStatistics(year, month));
    }
}
