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
            BadgeAcquireRequest badgeAcquireRequest = new BadgeAcquireRequest();
            badgeAcquireRequest.setMemberId(memberId);
            badgeAcquireRequest.setCount(memberAttendance.getCount());
            badgeAcquireRequest.setConsecutiveDays(memberAttendance.getConsecutiveDays());
            result = badgeAcquireDAO.updateAttendance(badgeAcquireRequest);
        }
        if(result == 0) {
            throw new IllegalStateException("출석 기록 중 문제가 발생하였습니다");
        }
    }
}
