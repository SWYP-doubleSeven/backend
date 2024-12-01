package com.swyp.doubleSeven.domain.badgeCount.controller;

import com.swyp.doubleSeven.domain.badgeCount.service.BadgeCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BadgeCountController {

    private final BadgeCountService badgeCountService;

    /* 출석 카운트 증가 */
    public void insertUserAttendance(Integer memberId) {
        badgeCountService.updateAttendance(memberId);
    }

    /* 기록 카운트 증가 */
    public void upsertLogCount() {

    }

    /* 금액 카운트 증가 */
    public void upsertTotalMoney() {

    }




}
