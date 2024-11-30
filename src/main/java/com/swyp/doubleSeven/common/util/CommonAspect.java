package com.swyp.doubleSeven.common.util;

import com.swyp.doubleSeven.domain.badgeAcquire.service.BadgeAcquireService;
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

    private final BadgeAcquireService badgeAcquireService;

    /* 로그인시 연속출석 기록 */
    @Before("execution(* com.swyp.doubleSeven.domain.member.kakao.controller.MemberController.kakaoLogin(..))")
    public void afterLogin() {
        // todo : 전체 로그인기능 구현 후 경로 합치기
//        Integer memberId = (Integer)(httpServletRequest.getSession().getAttribute("memberId"));
        Integer memberId = 3; // todo : 운영 올리고 주석해제후 다시 테스트해야함
        if(memberId == null) {
            throw new IllegalStateException("세션에 memberId가 없습니다. 로그인 상태를 확인하세요.");
        }

        badgeAcquireService.updateAttendance(memberId);
    }
}
