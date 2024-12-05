package com.swyp.doubleSeven.domain.saving.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "가상 소비 등록/수정 요청")
public class SavingRequest {

    @Schema(description = "멤버 ID", example = "1")
    private Integer memberId;

    private Integer savingId;

    @Schema(description = "가상 소비 일자", example = "2024-03-27T10:00:00")
    private LocalDateTime savingYmd; // 가상 소비 일자

    @Schema(description = "카테고리명", example = "식비")
    private String categoryName; // 하위 카테고리명

    @Schema(description = "가상 소비 금액", example = "meal")
    private int amount; // 가상 소비 금액

    @Builder
    public SavingRequest(Integer memberId, LocalDateTime savingYmd, String categoryName, int amount) {
        this.memberId = memberId;
        this.savingYmd = savingYmd;
        this.categoryName = categoryName;
        this.amount = amount;
    }
}
