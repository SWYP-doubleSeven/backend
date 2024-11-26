package com.swyp.doubleSeven.domain.saving.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class SavingListResponse {

    private Integer savingId;           // 가상 소비 ID

    private LocalDateTime savingYmd;    // 가상 소비 일자

    private double amount;              // 가상 소비 금액

    private String memo;                // 메모

    private int mainCategoryId;     // 상위 카테고리 ID

    private String mainCategoryName;    // 상위 카테고리명

    private int subCategoryId;      // 하위 카테고리 ID

    private String subCategoryName;     // 하위 카테고리명

    @Builder
    public SavingListResponse(Integer savingId, LocalDateTime savingYmd, double amount, String memo, Integer mainCategoryId, String mainCategoryName, Integer subCategoryId, String subCategoryName) {
        this.savingId = savingId;
        this.savingYmd = savingYmd;
        this.amount = amount;
        this.memo = memo;
        this.mainCategoryId = mainCategoryId;
        this.mainCategoryName = mainCategoryName;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
    }
}
