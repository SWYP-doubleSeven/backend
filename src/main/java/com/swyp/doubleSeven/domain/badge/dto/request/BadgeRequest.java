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
    private String badgeTypeKr;
    private String badgeDescription;
    private String operator;
    private String value;
    private Integer memberId;


    public void setBadgeId(Integer badgeId){ this.badgeId = badgeId; }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public void setEmblemPath(String emblemPath) {this.emblemPath = emblemPath; }
}
