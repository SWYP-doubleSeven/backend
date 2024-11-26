package com.swyp.doubleSeven.domain.saving.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;

public class SavingUpdateRequest {

    private Integer savingId; // 가상 소비 ID

    private LocalDateTime savingYmd; // 가상 소비 일자

    private int mainCategoryId; // 상위 카테고리

    private int subCategoryId; // 하위 카테고리

    private double amount; // 가상 소비 금액

    private String memo; // 메모

    @Builder
    public SavingUpdateRequest(Integer savingId, LocalDateTime savingYmd, int mainCategoryId, int subCategoryId, double amount, String memo) {
        this.savingId = savingId;
        this.savingYmd = savingYmd;
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
        this.amount = amount;
        this.memo = memo;
    }
}
