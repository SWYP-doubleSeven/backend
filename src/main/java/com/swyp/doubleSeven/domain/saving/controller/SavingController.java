package com.swyp.doubleSeven.domain.saving.controller;

import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import com.swyp.doubleSeven.domain.saving.service.SavingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 가상 소비 단건 조회
    @GetMapping("/{savingId}")
    public ResponseEntity<SavingResponse> getVirtualItem(@PathVariable Integer savingId, Integer memberId) {
        return ResponseEntity.ok(savingService.getVirtualItem(savingId, memberId));
    }

    // 가상 소비 삭제
    @DeleteMapping("/{savingId}")
    public ResponseEntity<Void> deleteVirtualItem (@PathVariable Integer savingId) {
        savingService.deleteVirtualItem(savingId);
        return ResponseEntity.noContent().build();
    }

}
