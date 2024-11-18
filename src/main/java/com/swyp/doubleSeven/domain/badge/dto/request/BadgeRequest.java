package com.swyp.doubleSeven.domain.badge.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeRequest {
    private Integer badgeId;
    private String badgeName;
    private String emblemPath;
    private String badgeType;
    private Integer mainCategoryId;
    private String operator;
    private Integer value;
    private Integer userId;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
