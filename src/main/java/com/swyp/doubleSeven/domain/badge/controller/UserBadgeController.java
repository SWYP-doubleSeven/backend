package com.swyp.doubleSeven.domain.badge.controller;

import com.swyp.doubleSeven.common.aspect.AuthenticationAspect;
import com.swyp.doubleSeven.common.aspect.AuthenticationUtil;
import com.swyp.doubleSeven.common.util.CommonUtil;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.badge.service.UserBadgeService;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
public class UserBadgeController {

    private final UserBadgeService service;
    private final AuthenticationUtil authenticationUtil;

    @GetMapping("/{badgeId}")
    @Operation(summary = "사용자-뱃지 단건조회",
            description = "사용자가 뱃지 하나를 조회합니다. (로그인/소비기록 후 자동으로 뱃지정보가 조회되므로 잘 쓰지 않는 api)")
    @ApiResponse(
            responseCode = "200",
            description = "뱃지 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BadgeResponse.class),
                    examples = @ExampleObject(
                            value = """
                            {
                               "badgeId": 2,
                               "badgeName": "가치를 새기다",
                               "emblemPath": "https://aws.s3.address/badge_image/badge_mark+the+moment.png",
                               "badgeType": "LOG",
                               "badgeTypeKr": "제로코스트와 함께",
                               "badgeDescription": "처음 가계부 작성",
                               "operator": ">=",
                               "value": "1",
                               "count": 0,
                               "rgstId": 0,
                               "rgstDt": "2024-11-30 20:54:05",
                               "updtId": 0,
                               "updtDt": "2024-11-30 20:54:05",
                               "acquireYN": "Y"
                             }
                            """
                    )
            )
    )
    public ResponseEntity<BadgeResponse> getBadge(@PathVariable Integer badgeId) {

        Integer currentMemberId = authenticationUtil.getCurrentMemberId();
        BadgeRequest request = new BadgeRequest();
        request.setBadgeId(badgeId);
        request.setMemberId(currentMemberId);
        return ResponseEntity.status(HttpStatus.OK).body(service.getBadge(request));
    }




    @GetMapping("/list")
    @Operation(summary = "사용자-뱃지 목록조회",
            description = "사용자가 뱃지 목록을 조회합니다. 사용자가 획득하지 않은 특정날짜(1/1, 10/31, ..), 매달 최고 금액 저축 뱃지는 제외하고, " +
                    "사용자의 획득 여부를 같이 반환합니다")
    @ApiResponse(
            responseCode = "200",
            description = "뱃지 목록 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BadgeResponse.class),
                    examples = @ExampleObject(
                            value = """
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
                                      "count": 0,
                                      "rgstId": 3,
                                      "rgstDt": "2024-11-30 20:54:05",
                                      "updtId": 3,
                                      "updtDt": "2024-11-30 20:54:05",
                                      "acquireYN": "N"
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
                                      "count": 0,
                                      "rgstId": 3,
                                      "rgstDt": "2024-11-30 20:54:05",
                                      "updtId": 3,
                                      "updtDt": "2024-11-30 20:54:05",
                                      "acquireYN": "N"
                                    }]
                            """
                    )
            )
    )
    public ResponseEntity<List<BadgeResponse>> getBadgeList() {
        Integer memberId = authenticationUtil.getCurrentMemberId();
        return ResponseEntity.status(HttpStatus.OK).body(service.getBadgeList(memberId));
    }
}


