package com.swyp.doubleSeven.domain.saving.controller;

import com.swyp.doubleSeven.domain.common.enums.SortType;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingCalendarResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingListResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import com.swyp.doubleSeven.domain.saving.service.SavingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/virtual-items")
@Tag(name = "Virtual Items", description = "가상 소비 관리 API")
public class SavingController {

    private final SavingService savingService;

    // 가상 소비 등록
    @Operation(summary = "가상 소비 등록", description = "새로운 가상 소비 항목을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "가상 소비 등록 성공"),
            //@ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<Void> createVirtualItem (@RequestBody SavingRequest savingRequest) {
        savingService.createVirtualItem(savingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 가상 소비 조회 (월별 => 일자별 합계)
    @Operation(summary = "캘린더뷰", description = "특정 연월의 기록을 캘린더 형식으로 조회가능합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SavingCalendarResponse.class))),
            //@ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping ("/calendar/{year}/{month}")
    public ResponseEntity<SavingCalendarResponse> getVirtualItemMonthly (
            @Parameter(description = "조회할 연도 (예: 2024)", in = ParameterIn.PATH) @PathVariable int year,
            @Parameter(description = "조회할 월 (1-12)", in = ParameterIn.PATH) @PathVariable int month,
            @Parameter(description = "카테고리 필터 (예: meal, taxi)", required = false)
            @RequestParam(required = false) String categoryName) {
        return ResponseEntity.ok(savingService.getVirtualItemMonthly(year, month, categoryName));
    }

    // 가상 소비 조회 (리스트)
    @Operation(summary = "가상 소비 목록 조회", description = "가상 소비 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SavingResponse.class))),
            //@ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping("/list/{year}/{month}")
    public ResponseEntity<SavingListResponse> getSavingList(
            @Parameter(description = "조회할 연도 (예: 2024)", in = ParameterIn.PATH) @PathVariable int year,
            @Parameter(description = "조회할 월 (1-12)", in = ParameterIn.PATH) @PathVariable int month,
            @Parameter(description = "정렬 기준 (latest:최신순, oldest:오래된순, amount_desc:금액높은순, amount_asc:금액낮은순)")
            @RequestParam(required = false, defaultValue = "latest") String sort) {

        SortType sortType = validateAndGetSortType(sort);
        return ResponseEntity.ok(savingService.getVirtualItemList(year, month, sortType));
    }

    // enum 맵핑
    private SortType validateAndGetSortType(String sort) {
        return SortType.fromCode(sort);
    }

    // 가상 소비 단건 조회
    @Operation(summary = "가상 소비 상세 조회", description = "특정 가상 소비의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            //@ApiResponse(responseCode = "404", description = "가상 소비를 찾을 수 없음")
    })
    @GetMapping("/{savingId}")
    public ResponseEntity<SavingResponse> getVirtualItem (
            @Parameter(description = "가상 소비 ID", in = ParameterIn.PATH) @PathVariable Integer savingId) {
        return ResponseEntity.ok(savingService.getVirtualItem(savingId));
    }

    // 가상 소비 수정
    @Operation(summary = "가상 소비 수정", description = "특정 가상 소비의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            //@ApiResponse(responseCode = "404", description = "가상 소비를 찾을 수 없음")
    })
    @PutMapping("/{savingId}")
    public ResponseEntity<Void> updateVirtualItem (
            @Parameter(description = "가상 소비 ID", in = ParameterIn.PATH) @PathVariable Integer savingId,
            @Parameter(description = "수정할 가상 소비 정보") @RequestBody SavingRequest savingRequest) {
        savingService.updateVirtualItem(savingId, savingRequest);
        return ResponseEntity.ok().build();
    }

    // 가상 소비 삭제
    @Operation(summary = "가상 소비 삭제", description = "특정 가상 소비를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            //@ApiResponse(responseCode = "404", description = "가상 소비를 찾을 수 없음")
    })
    @DeleteMapping("/{savingId}")
    public ResponseEntity<Void> deleteVirtualItem (
            @Parameter(description = "가상 소비 ID", in = ParameterIn.PATH) @PathVariable Integer savingId) {
        savingService.deleteVirtualItem(savingId);
        return ResponseEntity.noContent().build();
    }

}
