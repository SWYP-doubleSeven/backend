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

    private int mainCategoryId; // 상위 카테고리

    private int subCategoryId; // 하위 카테고리

    private double amount; // 가상 소비 금액

    private String memo; // 메모

    @Builder
    public SavingRequest(LocalDateTime savingYmd, int mainCategoryId, int subCategoryId, double amount, String memo) {
        this.savingYmd = savingYmd;
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
        this.amount = amount;
        this.memo = memo;
    }
}
