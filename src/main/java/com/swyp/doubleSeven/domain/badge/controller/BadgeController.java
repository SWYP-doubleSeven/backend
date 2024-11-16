package com.swyp.doubleSeven.domain.badge.controller;

import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.badge.service.BadgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/badges")
public class BadgeController {

    private final BadgeService badgeService;

    // 뱃지 등록
    @PostMapping
    public ResponseEntity<BadgeResponse> insertBadge(@RequestBody BadgeRequest badgeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(badgeService.insertBadge(badgeRequest));
    }

    // 뱃지 수정
    @PutMapping
    public ResponseEntity<BadgeResponse> updateBadge(@RequestBody BadgeRequest badgeRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(badgeService.updateBadge(badgeRequest));
    }

    // 뱃지 삭제
    @DeleteMapping("/{badgeId}")
    public ResponseEntity<BadgeResponse> deleteBadge(@PathVariable Integer badgeId) {
        badgeService.deleteBadge(badgeId);
        return ResponseEntity.noContent().build();
    }

    // 뱃지 단건조회

    // 뱃지 목록 조회
}
