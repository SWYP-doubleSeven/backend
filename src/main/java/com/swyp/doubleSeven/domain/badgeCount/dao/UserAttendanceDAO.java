package com.swyp.doubleSeven.domain.badgeCount.dao;

import com.swyp.doubleSeven.domain.badgeAcquire.dto.request.UserAttendanceRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAttendanceDAO {

    int updateUserAttendance(UserAttendanceRequest userAttendanceRequest);
}
