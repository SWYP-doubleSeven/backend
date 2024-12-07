package com.swyp.doubleSeven.domain.saving.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "가상 소비 등록/수정 요청")
public class SavingRequest {

    @Schema(hidden = true)
    private Integer memberId;

    @Schema(hidden = true)
    private Integer savingId;

    @Schema(description = "가상 소비 일자", example = "2024-12-07")
    private LocalDate savingYmd; // 가상 소비 일자

    @Schema(hidden = true)
    private LocalTime savingTime;

    @Schema(description = "카테고리명", example = "meal")
    private String categoryName; // 하위 카테고리명

    @Schema(description = "가상 소비 금액", example = "10000")
    private int amount; // 가상 소비 금액

    @Builder
    public SavingRequest(Integer memberId, LocalDate savingYmd, LocalTime savingTime, String categoryName, int amount) {
        this.memberId = memberId;
        this.savingYmd = savingYmd;
        this.savingTime = savingTime;
        this.categoryName = categoryName;
        this.amount = amount;
    }
}
