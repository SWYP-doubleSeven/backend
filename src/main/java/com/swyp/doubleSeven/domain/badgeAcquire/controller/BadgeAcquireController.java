package com.swyp.doubleSeven.domain.badgeAcquire.controller;

import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.badge.service.AdminBadgeService;
import com.swyp.doubleSeven.domain.badgeAcquire.service.BadgeAcquireService;
import com.swyp.doubleSeven.domain.common.enums.BadgeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/badgeAcquire")
public class BadgeAcquireController {

    private final BadgeAcquireService badgeAcquireService;
    private final AdminBadgeService adminBadgeService;

    // 매달1일에 최고저축액 멤버 등록
    @Scheduled(cron = "0 0 0 1 * ?") // 매월 1일 0시 0분 0초에 실행
    public void insertBadgeAcquireByTopMoney() {
        LocalDate cureentDate = LocalDate.now().minusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String yyyymm = cureentDate.format(formatter);
        BadgeRequest badgeRequest = BadgeRequest.builder()
                .badgeName("이달의 저축왕")
                .emblemPath("https://zerocost-image-bucket.s3.ap-northeast-2.amazonaws.com/badge_image/badge_monthly+king.png")
                .badgeDescription("해당 월 제로코스트 사용자 중 최대 저축액 달성(희소성)")
                .badgeType(BadgeType.MAX_MONEY.getName())
                .operator("=")
                .value(yyyymm)
                .memberId(0)
                .build();
        BadgeResponse badgeResponse = adminBadgeService.insertBadge(badgeRequest); // 이달의 저축왕 뱃지 생성
        badgeAcquireService.insertBadgeAcquireByTopMoney(badgeResponse.getBadgeId()); // 이달의 저축왕 뱃지 선정
    }

}
