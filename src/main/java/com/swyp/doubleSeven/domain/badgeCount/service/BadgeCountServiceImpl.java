package com.swyp.doubleSeven.domain.badgeCount.service;

import com.swyp.doubleSeven.domain.badgeCount.dao.BadgeCountDAO;
import com.swyp.doubleSeven.domain.badgeCount.dto.request.BadgeCountRequest;
import com.swyp.doubleSeven.domain.badgeCount.dto.response.AttendanceResponse;
import com.swyp.doubleSeven.domain.badgeCount.dto.response.BadgeCountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

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
    public int upsertBadgeCount(BadgeCountRequest badgeCountRequest) {
        BadgeCountResponse badgeCount = badgeCountDAO.getBadgeCount(badgeCountRequest);
        int result = 0;
        if(badgeCount == null) { // insert
            return badgeCountDAO.insertBadgeCount(badgeCountRequest);
        } else { // update
            badgeCountRequest.setCountId(badgeCount.getCountId());
            return badgeCountDAO.updateBadgeCount(badgeCountRequest);
        }
    }

    public BadgeCountResponse getBadgeCount(BadgeCountRequest badgeCountRequest) {
        return badgeCountDAO.getBadgeCount(badgeCountRequest);
    }

}
