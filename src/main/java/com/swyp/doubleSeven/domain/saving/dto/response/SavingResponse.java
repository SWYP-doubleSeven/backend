package com.swyp.doubleSeven.domain.saving.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SavingResponse {

    private Integer savingId; // 가상 소비 ID

    private LocalDateTime savingYmd; // 가상 소비 일자

    private int largeCategoryId; // 상위 카테고리

    private int smallCategoryId; // 하위 카테고리

    private int amount; // 가상 소비 금액

    private String memo; // 메모

    @Builder
    public SavingResponse(Integer savingId, LocalDateTime savingYmd, int largeCategoryId, int smallCategoryId, int amount, String memo) {
        this.savingId = savingId;
        this.savingYmd = savingYmd;
        this.largeCategoryId = largeCategoryId;
        this.smallCategoryId = smallCategoryId;
        this.amount = amount;
        this.memo = memo;
    }
}
