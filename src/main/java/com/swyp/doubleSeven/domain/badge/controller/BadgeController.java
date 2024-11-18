package com.swyp.doubleSeven.domain.badge.controller;

import com.swyp.doubleSeven.common.util.CommonUtil;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeSearchCriteria;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.badge.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/badges")
@Tag(name = "Badge API", description = "뱃지 관련 API")
public class BadgeController {

    private final BadgeService badgeService;

    // TODO : 뱃지 등록/수정/삭제: 사용자의 role검증 추가
    // TODO : 게스트는 뱃지를 발급받을수 없잖아.

    @PostMapping
    @Operation(summary = "뱃지 등록", description = "관리자가 뱃지를 등록합니다")
    public ResponseEntity<BadgeResponse> insertBadge(
            @RequestBody BadgeRequest badgeRequest
            , @RequestParam(required = false) Integer userId // 테스트용
            , HttpSession session
    ) {
        // userId 셋팅
        userId = userId != null ? userId : (Integer) session.getAttribute("userId");
        badgeRequest.setUserId(userId);

        // 관리자 권한 검증
        CommonUtil.validateAdminAccess(session);

        return ResponseEntity.status(HttpStatus.CREATED).body(badgeService.insertBadge(badgeRequest));
    }

    @PutMapping
    @Operation(summary = "뱃지 수정", description = "관리자가 뱃지를 수정합니다")
    public ResponseEntity<BadgeResponse> updateBadge(
            @RequestBody BadgeRequest badgeRequest
            , @RequestParam(required = false) Integer userId // 테스트용
            , HttpSession session
    ) {
        // userId 셋팅
        userId = userId != null ? userId : (Integer) session.getAttribute("userId");
        badgeRequest.setUserId(userId);

        // 관리자 권한 검증
        CommonUtil.validateAdminAccess(session);

        return ResponseEntity.status(HttpStatus.OK).body(badgeService.updateBadge(badgeRequest));
    }

    @DeleteMapping("/{badgeId}")
    @Operation(summary = "뱃지 삭제", description = "관리자가 뱃지를 삭제합니다")
    public ResponseEntity<BadgeResponse> deleteBadge(@PathVariable Integer badgeId, HttpSession session) {

        // 관리자 권한 검증
        CommonUtil.validateAdminAccess(session);

        badgeService.deleteBadge(badgeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{badgeId}")
    @Operation(summary = "뱃지 단건조회", description = "뱃지 한 개를 조회합니다")
    public ResponseEntity<BadgeResponse> getBadge(@PathVariable Integer badgeId) {
        return ResponseEntity.ok(badgeService.getBadge(badgeId));
    }

    @GetMapping
    @Operation(summary = "뱃지 목록조회", description = "뱃지 목록을 조회합니다")
    public ResponseEntity<List<BadgeResponse>> getBadgeList(@RequestBody BadgeSearchCriteria criteria) {
        return ResponseEntity.status(HttpStatus.OK).body(badgeService.getBadgeList(criteria));
    }
}
