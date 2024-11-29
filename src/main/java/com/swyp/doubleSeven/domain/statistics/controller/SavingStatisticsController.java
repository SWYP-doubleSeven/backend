package com.swyp.doubleSeven.domain.statistics.controller;

import com.swyp.doubleSeven.domain.statistics.dto.response.CategoryStatisticsResponse;
import com.swyp.doubleSeven.domain.statistics.dto.response.HourlyStatisticsResponse;
import com.swyp.doubleSeven.domain.statistics.dto.response.MonthlyTotalResponse;
import com.swyp.doubleSeven.domain.statistics.service.SavingStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Statistics API", description = "가상 소비 통계 관련 API")
public class SavingStatisticsController {

    private final SavingStatisticsService statisticsService;

    // 월별 총 절약 금액
    @Operation(summary = "월별 총 절약 금액 조회", description = "지정된 연도와 월의 총 절약 금액을 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = MonthlyTotalResponse.class))
            )
    })
    @GetMapping("/monthly-total/{year}/{month}")
    public ResponseEntity<MonthlyTotalResponse> getMonthlyTotal(
            @Parameter(description = "조회할 연도", example = "2024") @PathVariable int year,
            @Parameter(description = "조회할 월(1-12)", example = "3") @PathVariable int month) {
        return ResponseEntity.ok(statisticsService.getMonthlyTotal(year, month));
    }

    // 카테고리별 통계
    @Operation(summary = "카테고리별 절약 통계 조회", description = "지정된 연도와 월의 카테고리별 절약 금액과 비율을 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = CategoryStatisticsResponse.class))
            )
    })
    @GetMapping("/category/{year}/{month}")
    public ResponseEntity<List<CategoryStatisticsResponse>> getCategoryStatistics(
            @Parameter(description = "조회할 연도", example = "2024") @PathVariable int year,
            @Parameter(description = "조회할 월(1-12)", example = "3") @PathVariable int month) {
        return ResponseEntity.ok(statisticsService.getCategoryStatistics(year, month));
    }

    // 시간대별 통계
    @Operation(summary = "시간대별 절약 통계 조회", description = "지정된 연도와 월의 시간대별 절약 금액과 건수를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = HourlyStatisticsResponse.class))
            )
    })
    @GetMapping("/hourly/{year}/{month}")
    public ResponseEntity<List<HourlyStatisticsResponse>> getHourlyStatistics(
            @Parameter(description = "조회할 연도", example = "2024") @PathVariable int year,
            @Parameter(description = "조회할 월(1-12)", example = "3") @PathVariable int month) {
        return ResponseEntity.ok(statisticsService.getHourlyStatistics(year, month));
    }
}
