package com.swyp.doubleSeven.domain.badge.service;

import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;

public interface BadgeService {

    // 뱃지 등록
    BadgeResponse insertBadge(BadgeRequest badgeRequest);

    // 뱃지 수정
    BadgeResponse updateBadge(BadgeRequest badgeRequest);

    // 뱃지 삭제
    int deleteBadge(Integer badgeId);

}
