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
}
