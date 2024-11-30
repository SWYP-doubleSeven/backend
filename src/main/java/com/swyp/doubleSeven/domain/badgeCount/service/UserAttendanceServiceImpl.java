package com.swyp.doubleSeven.domain.badgeCount.service;

import com.swyp.doubleSeven.domain.badgeAcquire.dto.request.UserAttendanceRequest;
import com.swyp.doubleSeven.domain.badgeCount.dao.UserAttendanceDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAttendanceServiceImpl implements UserAttendanceService{

    private final UserAttendanceDAO userAttendanceDAO;

    public int updateUserAttendance(UserAttendanceRequest userAttendanceRequest) {
        return userAttendanceDAO.updateUserAttendance(userAttendanceRequest);
    }
}
