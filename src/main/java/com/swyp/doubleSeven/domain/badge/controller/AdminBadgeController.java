package com.swyp.doubleSeven.domain.badge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.doubleSeven.common.aspect.AuthenticationAspect;
import com.swyp.doubleSeven.common.aspect.anotation.AuthCheck;
import com.swyp.doubleSeven.common.exception.BusinessException;
import com.swyp.doubleSeven.common.util.CommonImageUploader;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.badge.service.AdminBadgeService;
import com.swyp.doubleSeven.domain.common.enums.Error;
import com.swyp.doubleSeven.domain.common.enums.Role;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    private final AuthenticationAspect authenticationAspect;

    @PostMapping
//    @SecurityRequirement(name = "cookieAuth")
//    @AuthCheck(allowedRoles = Role.ADMIN)
    @Operation(summary = "뱃지 등록", description = "관리자가 뱃지를 등록합니다")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "등록 성공",
                content = @Content(mediaType = "application/json",
                        examples = @ExampleObject(value = """
                                 {
                                "badgeRequest": { 
                                    "badgeName": "가치를 새기다",
                                    "badgeType": "LOG",
                                    "badgeTypeKr": "제로코스트와 함께",
                                    "badgeDescription": "처음 가계부 작성",
                                    "operator": ">=",
                                    "value": "1" },
                                "boardImg": "<이미지 파일>"
                                 }
                                 """)))
        })
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

        if (!"ADMIN".equals(authenticationAspect.getRoleByMemberId(badgeRequest.getMemberId()))) {
            throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
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


    @PutMapping
//    @AuthCheck(allowedRoles = Role.ADMIN)
//    @SecurityRequirement(name = "cookieAuth")
    @Operation(summary = "뱃지 수정", description = "관리자가 뱃지를 수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        {
                            "badgeRequest": { 
                                "badgeId": 31,
                                "badgeName": "가치를 새기다",
                                "badgeType": "LOG",
                                "badgeTypeKr": "제로코스트와 함께",
                                "badgeDescription": "처음 가계부 작성",
                                "operator": ">=",
                                "value": "1" },
                            "boardImg": "<이미지 파일>"
                        }
                        """)))
    })
    public ResponseEntity<BadgeResponse> updateBadge(
            @RequestPart(value = "badgeRequest") String badgeRequestJson,
            @RequestPart(value = "boardImg", required = false) MultipartFile multipartFile
    ) {
        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        BadgeRequest badgeRequest;
        try {
            badgeRequest = objectMapper.readValue(badgeRequestJson, BadgeRequest.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (!"ADMIN".equals(authenticationAspect.getRoleByMemberId(badgeRequest.getMemberId()))) {
            throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
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
        return ResponseEntity.status(HttpStatus.OK).body(adminBadgeService.updateBadge(badgeRequest));
    }

    @DeleteMapping("/{badgeId}")
//    @AuthCheck(allowedRoles = Role.ADMIN)
//    @SecurityRequirement(name = "cookieAuth")
    @Operation(summary = "뱃지 삭제", description = "관리자가 뱃지를 삭제합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                 { "badgeId": 1 }
                                 """)))
    })
    public ResponseEntity<BadgeResponse> deleteBadge(@RequestBody BadgeRequest badgeRequest) {
        if (!"ADMIN".equals(authenticationAspect.getRoleByMemberId(badgeRequest.getMemberId()))) {
            throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
        }

        adminBadgeService.deleteBadge(badgeRequest.getBadgeId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list")
//    @AuthCheck(allowedRoles = Role.ADMIN)
//    @SecurityRequirement(name = "cookieAuth")
    @Operation(summary = "관리자-뱃지 목록조회", description = "관리자가 뱃지 목록을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                [
                                   {
                                     "badgeId": 1,
                                     "badgeName": "첫 만남",
                                     "emblemPath": "https://zerocost-image-bucket.s3.ap-northeast-2.amazonaws.com/badge_image/badge_first+step.png",
                                     "badgeType": "ATTENDANCE",
                                     "badgeTypeKr": "제로코스트와 함께",
                                     "badgeDescription": "처음 가입",
                                     "operator": ">=",
                                     "value": "1",
                                     "count": 35,
                                     "rgstId": 3,
                                     "rgstDt": "2024-11-30 20:54:05",
                                     "updtId": 3,
                                     "updtDt": "2024-11-30 20:54:05"
                                   },
                                   {
                                     "badgeId": 2,
                                     "badgeName": "가치를 새기다",
                                     "emblemPath": "https://zerocost-image-bucket.s3.ap-northeast-2.amazonaws.com/badge_image/badge_mark+the+moment.png",
                                     "badgeType": "LOG",
                                     "badgeTypeKr": "제로코스트와 함께",
                                     "badgeDescription": "처음 가계부 작성",
                                     "operator": ">=",
                                     "value": "1",
                                     "count": 9,
                                     "rgstId": 3,
                                     "rgstDt": "2024-11-30 20:54:05",
                                     "updtId": 3,
                                     "updtDt": "2024-11-30 20:54:05"
                                   }
                               ]
                        """)))
    })
    public ResponseEntity<List<BadgeResponse>> getBadgeList(@RequestParam("memberId") Integer memberId) {
        if (!"ADMIN".equals(authenticationAspect.getRoleByMemberId(memberId))) {
            throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(adminBadgeService.getBadgeList());
    }
}
