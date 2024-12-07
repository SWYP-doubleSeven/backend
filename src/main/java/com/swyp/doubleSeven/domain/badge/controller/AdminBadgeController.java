package com.swyp.doubleSeven.domain.badge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.doubleSeven.common.aspect.anotation.AuthCheck;
import com.swyp.doubleSeven.common.util.CommonImageUploader;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeSearchCriteria;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.badge.service.AdminBadgeService;
import com.swyp.doubleSeven.domain.common.enums.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/badges")
@Tag(name = "Badge API", description = "관리자의 뱃지 관련 API")
public class AdminBadgeController {

    private final AdminBadgeService adminBadgeService;

    private final CommonImageUploader commonImageUploader;

    @PutMapping
    @AuthCheck(allowedRoles = Role.ADMIN)
    @Operation(summary = "뱃지 등록", description = "관리자가 뱃지를 등록합니다")
    public ResponseEntity<BadgeResponse> insertBadge(
            @RequestPart(value = "badgeRequest") String badgeRequestJson,
            @RequestPart(value = "boardImg", required = false) MultipartFile multipartFile) {

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        BadgeRequest badgeRequest;
        try {
            badgeRequest = objectMapper.readValue(badgeRequestJson, BadgeRequest.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String fileName = "";
        // 파일 업로드 처리
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                // 파일 업로드
                fileName = commonImageUploader.upload(multipartFile, "badge_image"); // 업로드 폴더명
                badgeRequest.setEmblemPath(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null); // 업로드 실패 처리
            }
        }


        BadgeResponse badgeResponse = adminBadgeService.insertBadge(badgeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(badgeResponse);
    }

    @PostMapping
    @AuthCheck(allowedRoles = Role.ADMIN)
    @Operation(summary = "뱃지 수정", description = "관리자가 뱃지를 수정합니다")
    public ResponseEntity<BadgeResponse> updateBadge( @RequestBody BadgeRequest badgeRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(adminBadgeService.updateBadge(badgeRequest));
    }

    @DeleteMapping("/{badgeId}")
    @AuthCheck(allowedRoles = Role.ADMIN)
    @Operation(summary = "뱃지 삭제", description = "관리자가 뱃지를 삭제합니다")
    public ResponseEntity<BadgeResponse> deleteBadge(@PathVariable Integer badgeId) {
        adminBadgeService.deleteBadge(badgeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/list")
    @AuthCheck(allowedRoles = Role.ADMIN)
    @Operation(summary = "관리자-뱃지 목록조회", description = "관리자가 뱃지 목록을 조회합니다")
    public ResponseEntity<List<BadgeResponse>> getBadgeList(@RequestBody BadgeSearchCriteria criteria) {
        return ResponseEntity.status(HttpStatus.OK).body(adminBadgeService.getBadgeList(criteria));
    }
}
