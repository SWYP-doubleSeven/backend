package com.swyp.doubleSeven.domain.badgeCount.service;

import com.swyp.doubleSeven.domain.badgeCount.dao.BadgeCountDAO;
import com.swyp.doubleSeven.domain.badgeCount.dto.response.AttendanceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BadgeCountServiceImpl implements BadgeCountService {

    private final BadgeCountDAO badgeCountDAO;

    public void updateAttendance(Integer memberId) {
        AttendanceResponse memberAttendance = badgeCountDAO.getAttendance(memberId);
        int result = 0;
        if(memberAttendance == null) { // insert
            result = badgeCountDAO.insertAttendance(memberId);
        } else { // update
            if(memberAttendance.getTodayLogin() == 'Y') return; // 오늘 로그인했으면 건너뛰기

//            AttendanceRequest attendanceRequest = AttendanceRequest.builder()
//                    .memberId(memberId)
//                    .lastLoginDate(memberAttendance.getLastLoginDate())
//                    .count(memberAttendance.getCount())
//                    .consecutiveDays(memberAttendance.getConsecutiveDays())
//                    .build();
            result = badgeCountDAO.updateAttendance(memberId);
        }
        if(result == 0) {
            throw new IllegalStateException("출석 기록 중 문제가 발생하였습니다");
        }
    }

}
