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

    public int upsertMemberAttendance(Integer memberId) {
        AttendanceResponse memberAttendance = badgeCountDAO.getAttendance(memberId);
        int result = 0;
        if(memberAttendance == null) { // insert
            result = badgeCountDAO.insertAttendance(memberId);
        } else if(memberAttendance.getTodayLogin() != 'Y'){ // 오늘 로그인했으면 건너뛰기
            result = badgeCountDAO.updateAttendance(memberId);
        }
        return result;
    }
}
