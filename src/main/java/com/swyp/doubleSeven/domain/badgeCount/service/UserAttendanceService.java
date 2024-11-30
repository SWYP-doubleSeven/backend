package com.swyp.doubleSeven.domain.badgeCount.service;

import com.swyp.doubleSeven.domain.badgeAcquire.dto.request.UserAttendanceRequest;

public interface UserAttendanceService {

    int updateUserAttendance(UserAttendanceRequest userAttendanceRequest);
}
