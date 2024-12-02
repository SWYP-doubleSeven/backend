package com.swyp.doubleSeven.domain.badge.controller;

import com.swyp.doubleSeven.common.util.CommonUtil;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.badge.service.UserBadgeService;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/{badgeId}")
    @Operation(summary = "사용자-뱃지 단건조회", description = "사용자가 뱃지 하나를 조회합니다")
    public ResponseEntity<BadgeResponse> getBadge(@PathVariable Integer badgeId, HttpServletRequest httpServletRequest, HttpSession session) {

        if (CommonUtil.isLocalEnvironment(httpServletRequest)) {
            session.setAttribute("memberId", 1); // IP가 로컬일 때 memberId 설정
        }

        Integer memberId = (Integer)session.getAttribute("memberId");
        BadgeRequest request = new BadgeRequest();
        request.setBadgeId(badgeId);
        request.setMemberId(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(service.getBadge(request));
    }

    @GetMapping("/list")
    @Operation(summary = "사용자-뱃지 목록조회", description = "사용자가 뱃지 목록을 조회합니다")
    public ResponseEntity<List<BadgeResponse>> getBadgeList() {
        Integer memberId = 1; // todo : session에서 가져오는걸로 수정(-)
        return ResponseEntity.status(HttpStatus.OK).body(service.getBadgeList(memberId));
    }
}


