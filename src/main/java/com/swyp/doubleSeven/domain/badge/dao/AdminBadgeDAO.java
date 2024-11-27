package com.swyp.doubleSeven.domain.badge.dao;

import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeSearchCriteria;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Mapper
public interface AdminBadgeDAO {

    // 뱃지 등록
    void insertBadge(BadgeRequest badgeRequest);

    // 뱃지 수정
    int updateBadge(BadgeRequest badgeRequest);

    // 뱃지 삭제
    int deleteBadge(Integer badgeId);

    // 뱃지 단건 조회
    BadgeResponse getBadge(Integer badgeId);

    // 전체 뱃지 목록 조회
    List<BadgeResponse> getBadgeList(BadgeSearchCriteria criteria);

    // 전체 뱃지 목록 조회
    ResponseEntity<List<BadgeResponse>> getUserBadgeList(Integer memberId);
}
