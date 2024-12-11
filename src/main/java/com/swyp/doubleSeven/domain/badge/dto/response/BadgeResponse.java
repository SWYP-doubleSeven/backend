package com.swyp.doubleSeven.domain.badge.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "뱃지 응답 정보")
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값일시 응답에서 제외
public class BadgeResponse {
    @Schema(description = "뱃지 ID", example = "2")
    private Integer badgeId;

    @Schema(description = "뱃지 이름", example = "가치를 새기다")
    private String badgeName;

    @Schema(description = "엠블럼 경로", example = "https://aws.s3.address/badge_image/badge_mark+the+moment.png")
    private String emblemPath;

    @Schema(description = "뱃지 타입", example = "LOG")
    private String badgeType;

    @Schema(description = "뱃지 한글 타입", example = "제로코스트와 함께")
    private String badgeTypeKr;

    @Schema(description = "뱃지 설명", example = "처음 가계부 작성")
    private String badgeDescription;

    @Schema(description = "비교연산자", example = ">=")
    private String operator;

    @Schema(description = "값", example = "1")
    private String value;

    @Schema(description = "뱃지를 가진 유저의 수", example = "0")
    private int count;

    @Schema(description = "등록자 ID")
    private Integer rgstId;

    @Schema(description = "등록일시", example = "2024-11-30 20:54:05")
    private String rgstDt;

    @Schema(description = "수정자 ID")
    private Integer updtId;

    @Schema(description = "수정일시")
    private String updtDt;

    @Schema(description = "뱃지 획득 여부", example = "Y")
    private String acquireYN;
}
