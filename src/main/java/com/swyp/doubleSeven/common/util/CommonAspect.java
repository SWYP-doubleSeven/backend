package com.swyp.doubleSeven.common.util;

import com.swyp.doubleSeven.domain.badgeCount.dto.request.BadgeCountRequest;
import com.swyp.doubleSeven.domain.badgeCount.service.BadgeCountService;
import com.swyp.doubleSeven.domain.common.enums.BadgeType;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class CommonAspect {

    private final HttpServletRequest httpServletRequest;

    private final BadgeCountService badgeCountService;

    /* 로그인시 연속출석 기록, 출석카운트 증가 */
    @Before("execution(* com.swyp.doubleSeven.domain.member.controller.MemberController.kakaoLogin(..))")
    public void afterLogin() {
        // todo : 전체 로그인기능 구현 후 경로 합치기
//        Integer memberId = (Integer)(httpServletRequest.getSession().getAttribute("memberId"));
        Integer memberId = 3; // todo : 운영 올리고 주석해제후 다시 테스트해야함
        if(memberId == null) {
            throw new IllegalStateException("세션에 memberId가 없습니다. 로그인 상태를 확인하세요.");
        }
        int result = badgeCountService.upsertMemberAttendance(memberId);
        if(result > 0) { // 당일 로그인 2회이상->카운트X
            BadgeCountRequest badgeCountRequest = BadgeCountRequest.builder()
                    .memberId(memberId)
                    .badgeType(BadgeType.ATTENDANCE.getName())
                    .count(1)
                    .build();
            badgeCountService.upsertBadgeCount(badgeCountRequest);
        }
    }

    public void afterSaving(SavingRequest savingRequest) {
//        Integer memberId = (Integer)(httpServletRequest.getSession().getAttribute("memberId"));
        Integer memberId = 3; // todo
        BadgeCountRequest badgeCountRequest = new BadgeCountRequest();
        if(memberId == null) {
            throw new IllegalStateException("세션에 memberId가 없습니다. 로그인 상태를 확인하세요.");
        }

        badgeCountRequest.setMemberId(memberId);
        badgeCountRequest.setBadgeType(BadgeType.LOG.getName());
        badgeCountRequest.setCount(1);
        badgeCountRequest.setMemberId(memberId);
        badgeCountService.upsertBadgeCount(badgeCountRequest); // 기록 카운트

        badgeCountRequest.setCount(savingRequest.getAmount());
        badgeCountRequest.setBadgeType(BadgeType.MONEY.getName());
        badgeCountService.upsertBadgeCount(badgeCountRequest); // 금액 카운트
    }
}
