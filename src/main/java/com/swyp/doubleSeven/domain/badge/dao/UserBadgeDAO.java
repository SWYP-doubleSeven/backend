package com.swyp.doubleSeven.domain.badge.dao;

import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserBadgeDAO {

    // 뱃지 단건 조회
    BadgeResponse getBadge(BadgeRequest request);

    // 뱃지 목록 조회
    List<BadgeResponse> getBadgeList(Integer memberId);

    List<BadgeResponse> getBadgeIdAfterSaving(BadgeRequest badgeRequest);

    List<BadgeResponse> getBadgeIdAfterLogin(BadgeRequest badgeRequest);
}
