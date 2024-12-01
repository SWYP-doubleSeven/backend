package com.swyp.doubleSeven.domain.badgeCount.dao;

import com.swyp.doubleSeven.domain.badgeCount.dto.response.AttendanceResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BadgeCountDAO {

    int updateAttendance(Integer memberId);

    int insertAttendance(Integer memberId);

    AttendanceResponse getAttendance(Integer memberId);
}
