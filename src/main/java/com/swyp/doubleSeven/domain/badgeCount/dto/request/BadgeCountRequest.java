package com.swyp.doubleSeven.domain.badgeCount.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeCountRequest {

    private Integer countId;
    private Integer memberId;
    private String badgeType;
    private int count;
}
