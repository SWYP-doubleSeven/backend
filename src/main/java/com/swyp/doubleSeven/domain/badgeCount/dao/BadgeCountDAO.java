package com.swyp.doubleSeven.domain.badgeCount.dao;

import com.swyp.doubleSeven.domain.badgeCount.dto.request.BadgeCountRequest;
import com.swyp.doubleSeven.domain.badgeCount.dto.response.AttendanceResponse;
import com.swyp.doubleSeven.domain.badgeCount.dto.response.BadgeCountResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BadgeCountDAO {

    /* 연속 출석기록(MEMBER_ATTENDANCE 관련) */
    int updateAttendance(Integer memberId);
    int insertAttendance(Integer memberId);
    AttendanceResponse getAttendance(Integer memberId);

}
