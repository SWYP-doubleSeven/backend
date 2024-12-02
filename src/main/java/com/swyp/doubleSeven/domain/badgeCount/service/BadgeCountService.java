package com.swyp.doubleSeven.domain.badgeCount.service;

import com.swyp.doubleSeven.domain.badgeCount.dto.request.BadgeCountRequest;
import com.swyp.doubleSeven.domain.badgeCount.dto.response.BadgeCountResponse;

import java.sql.SQLException;

public interface BadgeCountService {

    int upsertMemberAttendance(Integer memberId);

    int upsertBadgeCount(BadgeCountRequest badgeCountRequest);

    BadgeCountResponse getBadgeCount(BadgeCountRequest badgeCountRequest);

}
