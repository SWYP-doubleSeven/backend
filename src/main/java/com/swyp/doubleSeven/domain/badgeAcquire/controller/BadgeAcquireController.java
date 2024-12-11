package com.swyp.doubleSeven.domain.badgeAcquire.controller;

import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.badge.service.AdminBadgeService;
import com.swyp.doubleSeven.domain.badge.service.UserBadgeService;
import com.swyp.doubleSeven.domain.badgeAcquire.dto.request.BadgeAcquireRequest;
import com.swyp.doubleSeven.domain.badgeAcquire.service.BadgeAcquireService;
import com.swyp.doubleSeven.domain.common.enums.BadgeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/badgeAcquire")
public class BadgeAcquireController {

    private final BadgeAcquireService badgeAcquireService;
    private final AdminBadgeService adminBadgeService;

    private final UserBadgeService userBadgeService;

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

        List<Integer> maxMoneyMemberList = badgeAcquireService.getMaxMoneyMemberList(); // max값이 같다면 중복선정 가능
        if(maxMoneyMemberList.size() == 0) return;
        for(Integer memberId: maxMoneyMemberList) {
            BadgeAcquireRequest badgeAcquireRequest = new BadgeAcquireRequest();
            badgeAcquireRequest.setBadgeId(badgeResponse.getBadgeId());
            badgeAcquireRequest.setMemberId(memberId);
            badgeAcquireService.insertBadgeAcquire(badgeAcquireRequest); // 이달의 저축왕 뱃지 선정
        }
    }

    public List<BadgeResponse> insertBadgeAcquireAfterLogin(Integer memberId, String yyyymmdd) {

        if ("01-01".equals(yyyymmdd.substring(5)) || "10-31".equals(yyyymmdd.substring(5))) {

            BadgeResponse badgeResponse = null;
            List<BadgeResponse> badgeResponseList = adminBadgeService.getBadgeList();
            if(badgeResponseList.size() > 0) {
                badgeResponse = badgeResponseList.get(0);
            };

            if(badgeResponse == null) {
                if ("01-01".equals(yyyymmdd.substring(5))) {
                    BadgeRequest badgeRequest = BadgeRequest.builder()
                            .badgeName("굿 스타트")
                            .emblemPath("https://zerocost-image-bucket.s3.ap-northeast-2.amazonaws.com/badge_image/badge_good+start.png")
                            .badgeType("DATE")
                            .operator("=")
                            .value(yyyymmdd)
                            .memberId(0)
                            .build();
                    badgeResponse = adminBadgeService.insertBadge(badgeRequest);
                } else {
                    BadgeRequest badgeRequest = BadgeRequest.builder()
                            .badgeName("세계 저축의 날")
                            .emblemPath("https://zerocost-image-bucket.s3.ap-northeast-2.amazonaws.com/badge_image/badge_savings+day.png")
                            .badgeType("DATE")
                            .operator("=")
                            .value(yyyymmdd)
                            .memberId(0)
                            .build();
                    badgeResponse = adminBadgeService.insertBadge(badgeRequest);
                }
            }
            BadgeAcquireRequest badgeAcquireRequest = new BadgeAcquireRequest();
            badgeAcquireRequest.setBadgeId(badgeResponse.getBadgeId());
            badgeAcquireRequest.setMemberId(memberId);
            badgeAcquireService.insertBadgeAcquire(badgeAcquireRequest);
        }

        BadgeRequest badgeRequest = new BadgeRequest();
        badgeRequest.setMemberId(memberId);
        List<BadgeResponse> badgeList = userBadgeService.getBadgeIdAfterLogin(badgeRequest);

        BadgeAcquireRequest badgeAcquireRequest = new BadgeAcquireRequest();
        badgeAcquireRequest.setMemberId(memberId);
        for(int i = 0; i< badgeList.size(); i++) {
            badgeAcquireRequest.setBadgeId(badgeList.get(i).getBadgeId());
            badgeAcquireService.insertBadgeAcquire(badgeAcquireRequest);
        }
        return badgeList;
    }

    public List<BadgeResponse> insertBadgeAcquireAfterSaving(Integer memberId) {
        BadgeRequest badgeRequest = new BadgeRequest();
        badgeRequest.setMemberId(memberId);
        List<BadgeResponse> badgeList = userBadgeService.getBadgeIdAfterSaving(badgeRequest);

        BadgeAcquireRequest badgeAcquireRequest = new BadgeAcquireRequest();
        badgeAcquireRequest.setMemberId(memberId);
        for(int i = 0; i< badgeList.size(); i++) {
            badgeAcquireRequest.setBadgeId(badgeList.get(i).getBadgeId());
            badgeAcquireService.insertBadgeAcquire(badgeAcquireRequest);
        }
        return badgeList;
    }
}
