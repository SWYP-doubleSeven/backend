package com.swyp.doubleSeven.domain.badgeCount.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeCountResponse {

    private Integer countId; // 고유id(pk)
    private Integer memberId;
    private String badgeType;
    private String count;
}
