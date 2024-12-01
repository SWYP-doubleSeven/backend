package com.swyp.doubleSeven.domain.badgeCount.controller;

import com.swyp.doubleSeven.domain.badgeCount.dto.request.BadgeCountRequest;
import com.swyp.doubleSeven.domain.badgeCount.dto.response.BadgeCountResponse;
import com.swyp.doubleSeven.domain.badgeCount.service.BadgeCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BadgeCountController {

    /* api통신을 하지 않고 내부 로직으로 처리합니다 */

    private final BadgeCountService badgeCountService;

    /* 연속출석 기록 */
    public int upsertMemberAttendance(Integer memberId) {
        return badgeCountService.upsertMemberAttendance(memberId);
    }

    /* 출석, 기록, 금액 카운트 증가 */
    public int  upsertBadgeCount(BadgeCountRequest badgeCountRequest) {
        return badgeCountService.upsertBadgeCount(badgeCountRequest);
    }

    /* 출석, 기록, 금액 조회 */
    public BadgeCountResponse getBadgeCount(BadgeCountRequest badgeCountRequest) {
        return badgeCountService.getBadgeCount(badgeCountRequest);
    }

}
