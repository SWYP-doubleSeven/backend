package com.swyp.doubleSeven.domain.saving.controller;

import com.swyp.doubleSeven.common.aspect.AuthenticationUtil;
import com.swyp.doubleSeven.common.aspect.anotation.AuthCheck;
import com.swyp.doubleSeven.common.aspect.anotation.VaildateResourceOwner;
import com.swyp.doubleSeven.domain.common.category.service.CategoryService;
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
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

    private final AuthenticationUtil authenticationUtil;

    // 가상 소비 등록
    @Operation(summary = "가상 소비 등록", description = "새로운 가상 소비 항목을 등록합니다. " +
            "글을 등록함과 동시에 뱃지 획득 조건에 달성하면 badgeResponseList에 응답값이 존재하고, 획득하는 뱃지가 없다면 []를 응답합니다.",
            security = {@SecurityRequirement(name = "cookieAuth")})
    @ApiResponse(
            responseCode = "201",
            description = "가상 소비 등록 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SavingResponse.class),
                    examples = @ExampleObject(
                            value = """
            {
                "memberId": 15,
                "savingId": 129,
                "savingYmd": "2024-12-03",
                "amount": 56330,
                "categoryName": "meal",
                "badgeResponseList": [
                    {
                        "badgeId": 2,
                        "badgeName": "가치를 새기다",
                        "emblemPath": "https://aws.s3.address/badge_image/badge_mark+the+moment.png",
                        "badgeType": "LOG",
                        "badgeDescription": "처음 가계부 작성",
                        "operator": ">=",
                        "value": "1",
                        "count": 0,
                        "rgstDt": "2024-11-30 20:54:05",
                        "acquireYN": "Y"
                    },
                    {
                        "badgeId": 5,
                        "badgeName": "절약의 시작",
                        "emblemPath": "https://aws.s3.address/badge_image/badge_saving+start.png",
                        "badgeType": "MONEY",
                        "badgeDescription": "만원 이상 절약",
                        "operator": ">=",
                        "value": "10000",
                        "count": 0,
                        "rgstDt": "2024-11-30 20:54:05",
                        "acquireYN": "Y"
                    }
                ]
            }
            """
                    )
            )
    )
    //@SecurityRequirement(name = "cookieAuth")
    //@AuthCheck
    @PostMapping
    public ResponseEntity<SavingResponse> createVirtualItem (@RequestBody SavingRequest savingRequest) {
        //log.info("가상소비저장로그 - {}", savingRequest.getMemberId(), savingRequest.getAmount());
        savingRequest.setMemberId(12);
        return ResponseEntity.status(HttpStatus.CREATED).body(savingService.createVirtualItem(savingRequest));
    }

    // 가상 소비 조회 (월별 => 일자별 합계)
    @Operation(summary = "캘린더뷰", description = "특정 연월의 기록을 캘린더 형식으로 조회가능합니다.",
            security = {@SecurityRequirement(name = "cookieAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                            {
                                "year": 2024,
                                "month": 12,
                                "days": [
                                    {
                                        "day": 3,
                                        "dayTotalAmount": 112660,
                                        "count": 2,
                                        "categorySummaries": [
                                            {
                                                "categoryName": "meal",
                                                "categoryTotalAmount": 56330
                                            },
                                            {
                                                "categoryName": "taxi",
                                                "categoryTotalAmount": 56330
                                            }
                                        ],
                                        "items": [
                                            {
                                                "memberId": 15,
                                                "savingId": 124,
                                                "savingYmd": "2024-12-03",
                                                "savingTime": "12:56:00",
                                                "amount": 56330,
                                                "categoryName": "meal"
                                            },
                                            {
                                                "memberId": 15,
                                                "savingId": 32,
                                                "savingYmd": "2024-12-03",
                                                "savingTime": "15:34:00",
                                                "amount": 56330,
                                                "categoryName": "taxi"
                                            }
                                        ]
                                    }
                                ]
                            }
                            """)))
    })
    //@SecurityRequirement(name = "cookieAuth")
    //@VaildateResourceOwner
    //@AuthCheck(validateAuthor = true) // 작성자 본인만 접근 가능
    @GetMapping ("/calendar/{year}/{month}")
    public ResponseEntity<SavingCalendarResponse> getVirtualItemMonthly (
            @Parameter(description = "조회할 연도 (예: 2024)", in = ParameterIn.PATH) @PathVariable int year,
            @Parameter(description = "조회할 월 (1-12)", in = ParameterIn.PATH) @PathVariable int month,
            @Parameter(description = "카테고리 필터 (예: meal, taxi)", required = false)
            @RequestParam(required = false) String categoryName) {
        Integer currentMemberId = 12;/*authenticationUtil.getCurrentMemberId();*/

        return ResponseEntity.ok(savingService.getVirtualItemMonthly(year, month, categoryName, currentMemberId));
    }

    // 가상 소비 조회 (리스트)
    @Operation(summary = "가상 소비 목록 조회", description = "가상 소비 목록을 조회합니다.",
            security = {@SecurityRequirement(name = "cookieAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                            {
                                "memberId": 17,
                                "totalAmount": 127960,
                                "dailyGroups": [
                                    {
                                        "day": 4,
                                        "items": [
                                            {
                                                "memberId": 17,
                                                "savingId": 48,
                                                "savingYmd": "2024-12-04",
                                                "savingTime": "13:01:00",
                                                "amount": 7000,
                                                "categoryName": "meal"
                                            }
                                        ]
                                    },
                                    {
                                        "day": 3,
                                        "items": [
                                            {
                                                "memberId": 17,
                                                "savingId": 26,
                                                "savingYmd": "2024-12-03",
                                                "savingTime": "13:01:00",
                                                "amount": 56330,
                                                "categoryName": "taxi"
                                            },
                                            {
                                                "memberId": 17,
                                                "savingId": 39,
                                                "savingYmd": "2024-12-03",
                                                "savingTime": "13:01:00",
                                                "amount": 8300,
                                                "categoryName": "dessert"
                                            },
                                            {
                                                "memberId": 17,
                                                "savingId": 41,
                                                "savingYmd": "2024-12-03",
                                                "savingTime": "13:01:00",
                                                "amount": 56330,
                                                "categoryName": "taxi"
                                            }
                                        ]
                                    }
                                ]
                            }
                            """)))
    })
    //@SecurityRequirement(name = "cookieAuth")
    //@VaildateResourceOwner
    //@AuthCheck(validateAuthor = true) // 작성자 본인만 접근 가능
    @GetMapping("/list/{year}/{month}")
    public ResponseEntity<SavingListResponse> getSavingList(
            @Parameter(description = "조회할 연도 (예: 2024)", in = ParameterIn.PATH) @PathVariable int year,
            @Parameter(description = "조회할 월 (1-12)", in = ParameterIn.PATH) @PathVariable int month) {
        Integer currentMemberId = 12;/*authenticationUtil.getCurrentMemberId();*/
        return ResponseEntity.ok(savingService.getVirtualItemList(year, month,/*, sortType,*/ currentMemberId));
    }

    // 가상 소비 단건 조회
    @Operation(summary = "가상 소비 상세 조회", description = "특정 가상 소비의 상세 정보를 조회합니다.",
            security = {@SecurityRequirement(name = "cookieAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                            {
                                "memberId": 17,
                                "savingId": 48,
                                "savingYmd": "2024-12-04",
                                "savingTime": "13:01:00",
                                "amount": 7000,
                                "categoryName": "meal"
                            }
                            """)))
    })
    //@SecurityRequirement(name = "cookieAuth")
    //@VaildateResourceOwner
    //@AuthCheck(validateAuthor = true) // 작성자 본인만 접근 가능
    @GetMapping("/{savingId}")
    public ResponseEntity<SavingResponse> getVirtualItem (
            @Parameter(description = "가상 소비 ID", in = ParameterIn.PATH) @PathVariable Integer savingId,
            HttpServletRequest request) {
        Integer currentMemberId = 12;/*authenticationUtil.getCurrentMemberId();*/

        return ResponseEntity.ok(savingService.getVirtualItem(savingId, currentMemberId));
    }

    // 가상 소비 수정
    @Operation(summary = "가상 소비 수정", description = "특정 가상 소비의 정보를 수정합니다.",
            security = {@SecurityRequirement(name = "cookieAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "가상 소비 수정 성공")))
    })
    //@SecurityRequirement(name = "cookieAuth")
    //@VaildateResourceOwner
    //@AuthCheck(validateAuthor = true) // 작성자 본인만 접근 가능
    @PutMapping("/{savingId}")
    public ResponseEntity<String> updateVirtualItem (
            @Parameter(description = "가상 소비 ID", in = ParameterIn.PATH) @PathVariable Integer savingId,
            @Parameter(description = "수정할 가상 소비 정보") @RequestBody SavingRequest savingRequest) {
        Integer currentMemberId = 12;
                //authenticationUtil.getCurrentMemberId();
        savingRequest.setMemberId(currentMemberId);
        log.info("가상소비 수정 getMemberid: {}", savingRequest.getMemberId());
        savingService.updateVirtualItem(savingId, savingRequest);
        return ResponseEntity.ok("가상 소비 수정 성공");
    }

    // 가상 소비 삭제 (소프트 삭제)
    @Operation(summary = "가상 소비 삭제", description = "특정 가상 소비를 삭제합니다.",
            security = {@SecurityRequirement(name = "cookieAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "가상 소비 삭제 성공")))
    })
    //@SecurityRequirement(name = "cookieAuth")
    //@VaildateResourceOwner
    //@AuthCheck(validateAuthor = true) // 작성자 본인만 접근 가능
    @DeleteMapping("/{savingId}")
    public ResponseEntity<String> deleteVirtualItem (
            @Parameter(description = "가상 소비 ID", in = ParameterIn.PATH) @PathVariable Integer savingId) {
        Integer currentMemberId = 12;
                //authenticationUtil.getCurrentMemberId();
        log.info("가상소비 삭제 memberid: {}", currentMemberId);
        savingService.deleteVirtualItem(savingId, currentMemberId);
        return ResponseEntity.ok("가상 소비 삭제 성공");
    }

    // 가상 소비 복구 (데이터 복구)
    @Operation(summary = "가상 소비 복구", description = "삭제한 가상소비를 되돌립니다.",
            security = {@SecurityRequirement(name = "cookieAuth")})    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "복구 성공",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "가상 소비 복구 성공")))
    })
    //@SecurityRequirement(name = "cookieAuth")
    //@VaildateResourceOwner
    //@AuthCheck(validateAuthor = true) // 작성자 본인만 접근 가능
    @PatchMapping("/{savingId}")
    public ResponseEntity<String> cancleSavingDelete (
            @Parameter(description = "가상 소비 ID", in = ParameterIn.PATH) @PathVariable Integer savingId) {
        Integer currentMemberId = 12;
                //authenticationUtil.getCurrentMemberId();
        log.info("가상소비 복구 memberid: {}", currentMemberId);
        savingService.cancleSavingDelete(savingId, currentMemberId);
        return ResponseEntity.ok("가상 소비 복구 성공");
    }

}
