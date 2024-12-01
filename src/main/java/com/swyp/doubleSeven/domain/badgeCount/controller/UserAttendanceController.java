package com.swyp.doubleSeven.domain.badgeCount.controller;

import com.swyp.doubleSeven.domain.badgeAcquire.dto.request.UserAttendanceRequest;
import com.swyp.doubleSeven.domain.badgeCount.service.UserAttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserAttendanceController {

    private final UserAttendanceService userAttendanceService;

    /* 출석 카운트 증가 */
    public void insertUserAttendance(UserAttendanceRequest userAttendanceRequest) {
        userAttendanceService.updateUserAttendance(userAttendanceRequest);
    }
}
