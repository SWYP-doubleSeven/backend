package com.swyp.doubleSeven.domain.badge.service;

import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeSearchCriteria;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;

import java.util.List;
import java.util.Map;

public interface BadgeService {

    // 뱃지 등록
    BadgeResponse insertBadge(BadgeRequest badgeRequest);

    // 뱃지 수정
    BadgeResponse updateBadge(BadgeRequest badgeRequest);

    // 뱃지 삭제
    int deleteBadge(Integer badgeId);

    // 뱃지 단건조회
    BadgeResponse getBadge(Integer badgeId);

    // 뱃지 목록 조회
    List<BadgeResponse> getBadgeList(BadgeSearchCriteria criteria);

}
