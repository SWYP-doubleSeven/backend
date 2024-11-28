package com.swyp.doubleSeven.domain.badge.controller;

import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeSearchCriteria;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.badge.service.AdminBadgeService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/admin/badges")
@Tag(name = "Badge API", description = "관리자의 뱃지 관련 API")
public class AdminBadgeController {

    private final AdminBadgeService adminBadgeService;

    @PutMapping
    @Operation(summary = "뱃지 등록", description = "관리자가 뱃지를 등록합니다")
    public ResponseEntity<BadgeResponse> insertBadge(@RequestBody BadgeRequest badgeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminBadgeService.insertBadge(badgeRequest));
    }

    @PostMapping
    @Operation(summary = "뱃지 수정", description = "관리자가 뱃지를 수정합니다")
    public ResponseEntity<BadgeResponse> updateBadge( @RequestBody BadgeRequest badgeRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(adminBadgeService.updateBadge(badgeRequest));
    }

    @DeleteMapping("/{badgeId}")
    @Operation(summary = "뱃지 삭제", description = "관리자가 뱃지를 삭제합니다")
    public ResponseEntity<BadgeResponse> deleteBadge(@PathVariable Integer badgeId) {
        adminBadgeService.deleteBadge(badgeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/list")
    @Operation(summary = "관리자-뱃지 목록조회", description = "관리자가 뱃지 목록을 조회합니다")
    public ResponseEntity<List<BadgeResponse>> getBadgeList(@RequestBody BadgeSearchCriteria criteria) {
        return ResponseEntity.status(HttpStatus.OK).body(adminBadgeService.getBadgeList(criteria));
    }
}
