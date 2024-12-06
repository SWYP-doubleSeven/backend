package com.swyp.doubleSeven.domain.badge.service;

import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;

import java.util.List;

public interface UserBadgeService {


    BadgeResponse getBadge(BadgeRequest request);

    // 뱃지 목록 조회
    List<BadgeResponse> getBadgeList(Integer memberId);

    List<BadgeResponse> getBadgeIdAfterSaving(BadgeRequest badgeRequest);

    List<BadgeResponse> getBadgeIdAfterLogin(BadgeRequest badgeRequest);
}
