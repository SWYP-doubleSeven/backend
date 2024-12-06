package com.swyp.doubleSeven.domain.badgeAcquire.dto.request;

import lombok.*;

@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeAcquireRequest {

    private Integer memberId;
    private Integer badgeId;
    private String badgeType;

    public String getBadgeType() {
        return this.badgeType;
    }
}
