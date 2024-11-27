package com.swyp.doubleSeven.domain.badgeAcquire.dao;

import com.swyp.doubleSeven.domain.badgeAcquire.dto.request.BadgeAcquireRequest;
import com.swyp.doubleSeven.domain.badgeAcquire.dto.response.BadgeAcquireResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BadgeAcquireDAO {

    int updateAttendance(BadgeAcquireRequest badgeAcquireRequest);

    int insertAttendance(Integer memberId);

    BadgeAcquireResponse getAttendance(Integer memberId);
}
