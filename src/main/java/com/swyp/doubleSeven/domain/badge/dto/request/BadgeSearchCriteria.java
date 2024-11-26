package com.swyp.doubleSeven.domain.badge.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeSearchCriteria {
    private String badgeName;
    private String badgeType;
    private String mainCategoryId;
    private String sortBy = "BADGE_ID";  // 기본 정렬 조건
    private String sortOrder = "ASC";    // 기본 정렬 순서
    private int limit = 30;              // 기본 페이지 크기
    private int offset = 0;              // 기본 오프셋
}
