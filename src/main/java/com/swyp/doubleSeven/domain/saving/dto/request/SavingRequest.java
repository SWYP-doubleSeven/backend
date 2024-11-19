package com.swyp.doubleSeven.domain.saving.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SavingRequest {

    private LocalDateTime savingYmd; // 가상 소비 일자

    @JsonProperty("lCategoryId")
    private int largeCategoryId; // 상위 카테고리

    @JsonProperty("sCategoryId")
    private int smallCategoryId; // 하위 카테고리

    private int amount; // 가상 소비 금액

    private String memo; // 메모

    @Builder
    public SavingRequest(LocalDateTime savingYmd, int largeCategoryId, int smallCategoryId, int amount, String memo) {
        this.savingYmd = savingYmd;
        this.largeCategoryId = largeCategoryId;
        this.smallCategoryId = smallCategoryId;
        this.amount = amount;
        this.memo = memo;
    }
}
