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
    private String badgeDescription;  // 뱃지 설명
    private String operator;         // 비교연산자
    private String value;           // 값
    private int count;              // 뱃지를 가진 유저의 수
    private Integer rgstId;         // 등록자 ID
    private String rgstDt;    // 등록일시
    private Integer updtId;          // 수정자 ID
    private String updtDt;    // 수정일시
    private String acquireYN; // 뱃지 획득 여부
}
