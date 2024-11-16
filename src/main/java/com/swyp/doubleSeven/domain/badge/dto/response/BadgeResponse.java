package com.swyp.doubleSeven.domain.badge.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeResponse {
    private Integer badgeId;         // 뱃지 ID
    private String badgeName;        // 뱃지 이름
    private String emblemPath;       // 엠블럼 경로
    private String badgeType;        // 뱃지 타입
    private Integer mainCategoryId;  // 대카테고리 ID
    private String operator;         // 비교연산자
    private Integer value;           // 값
    private Integer rgstId;         // 등록자 ID
    private LocalDateTime rgstDt;    // 등록일시
    private Integer updtId;          // 수정자 ID
    private LocalDateTime updtDt;    // 수정일시
}
