package com.swyp.doubleSeven.domain.saving.controller;

import com.swyp.doubleSeven.domain.common.enums.SortType;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingUpdateRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingCalendarResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingListResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import com.swyp.doubleSeven.domain.saving.service.SavingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/virtual-items")
public class SavingController {

    private final SavingService savingService;

    // 가상 소비 등록
    @PostMapping
    public ResponseEntity<SavingResponse> createVirtualItem (@RequestBody SavingRequest savingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(savingService.createVirtualItem(savingRequest));
    }

    // 가상 소비 조회 (월별 => 일자별 합계)
    @GetMapping ("/calendar/{year}/{month}")
    public ResponseEntity<SavingCalendarResponse> getVirtualItemMonthly (@PathVariable int year,@PathVariable int month) {
        return ResponseEntity.ok(savingService.getVirtualItemMonthly(year, month));
    }

    // 가상 소비 조회 (리스트)
    @GetMapping("/list/{year}/{month}")
    public ResponseEntity<List<SavingListResponse>> getSavingList(@PathVariable int year, @PathVariable int month,
            @RequestParam(required = false, defaultValue = "latest") String sort) {

        SortType sortType = validateAndGetSortType(sort);
        return ResponseEntity.ok(savingService.getVirtualItemList(year, month, sortType));
    }

    private SortType validateAndGetSortType(String sort) {
        return SortType.fromCode(sort);
    }

    /*// 가상 소비 조회 (캘린더 / 리스트)
    @GetMapping
    public ResponseEntity<List<SavingListResponse>> getVirtualItemList (
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer subCategoryId,
            @RequestParam(required = false, defaultValue = "latest") String sortTypeStr) {

        log.info("Controller - parameter: sortType: {}", sortTypeStr);
        SortType sortType = SortType.fromString(sortTypeStr);

        LocalDate yearMonth = null;
        if (year != null && month != null) {
            yearMonth = LocalDate.of(year, month, 1);
        }

        return ResponseEntity.ok(savingService.getVirtualItemList(yearMonth, subCategoryId, sortType));

    }*/

    // 가상 소비 단건 조회
    @GetMapping("/{savingId}")
    public ResponseEntity<SavingResponse> getVirtualItem (@PathVariable Integer savingId, Integer memberId) {
        return ResponseEntity.ok(savingService.getVirtualItem(savingId, memberId));
    }

    // 가상 소비 수정
    @PutMapping("/{savingId}")
    public ResponseEntity<Void> updateVirtualItem (@PathVariable Integer savingId, @RequestBody SavingUpdateRequest savingUpdateRequest) {
        savingService.updateVirtualItem(savingId, savingUpdateRequest);
        return ResponseEntity.ok().build();
    }

    // 가상 소비 삭제
    @DeleteMapping("/{savingId}")
    public ResponseEntity<Void> deleteVirtualItem (@PathVariable Integer savingId) {
        savingService.deleteVirtualItem(savingId);
        return ResponseEntity.noContent().build();
    }

}
