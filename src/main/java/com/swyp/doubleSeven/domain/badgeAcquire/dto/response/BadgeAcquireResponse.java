package com.swyp.doubleSeven.domain.badgeAcquire.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeAcquireResponse {
    private Integer memberId; // 사용자 id
    private String lastLoginDate; // 마지막 출석일
    private Integer count; // 출석 횟수
    private Integer consecutiveDays; // 연속 출석일수
}