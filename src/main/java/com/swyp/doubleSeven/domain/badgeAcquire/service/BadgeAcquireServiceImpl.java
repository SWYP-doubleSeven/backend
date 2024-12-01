package com.swyp.doubleSeven.domain.badgeAcquire.service;

import com.swyp.doubleSeven.domain.badgeAcquire.dao.BadgeAcquireDAO;
import com.swyp.doubleSeven.domain.badgeAcquire.dto.request.BadgeAcquireRequest;
import com.swyp.doubleSeven.domain.badgeAcquire.dto.response.BadgeAcquireResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BadgeAcquireServiceImpl implements BadgeAcquireService {

    // todo : 로그인 구현시 BadgeAcquireDAO 메서드 추가(-)

    private final BadgeAcquireDAO badgeAcquireDAO;

    public void updateAttendance(Integer memberId) {
        BadgeAcquireResponse memberAttendance = badgeAcquireDAO.getAttendance(memberId);
        int result = 0;
        if(memberAttendance == null) { // insert
            result = badgeAcquireDAO.insertAttendance(memberId);
        } else { // update
            if(memberAttendance.getTodayLogin() == 'Y') return; // 오늘 로그인했으면 건너뛰기

            BadgeAcquireRequest badgeAcquireRequest = BadgeAcquireRequest.builder()
                    .memberId(memberId)
                    .lastLoginDate(memberAttendance.getLastLoginDate())
                    .count(memberAttendance.getCount())
                    .consecutiveDays(memberAttendance.getConsecutiveDays())
                    .build();
            result = badgeAcquireDAO.updateAttendance(badgeAcquireRequest);
        }
        if(result == 0) {
            throw new IllegalStateException("출석 기록 중 문제가 발생하였습니다");
        }
    }
}
