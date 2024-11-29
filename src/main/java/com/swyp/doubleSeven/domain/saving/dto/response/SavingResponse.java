package com.swyp.doubleSeven.domain.saving.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@Schema(description = "가상 소비 응답")
public class SavingResponse {
    @Schema(description = "가상 소비 ID", example = "1")
    private Integer savingId;

    @Schema(description = "가상 소비 일자", example = "2024-03-27T10:00:00")
    private LocalDateTime savingYmd;

    @Schema(description = "가상 소비 금액", example = "10000")
    private int amount;

    @Schema(description = "카테고리명", example = "meal")
    private String categoryName;

    @Builder
    public SavingResponse(Integer savingId, LocalDateTime savingYmd, int amount, String categoryName) {
        this.savingId = savingId;
        this.savingYmd = savingYmd;
        this.amount = amount;
        this.categoryName = categoryName;
    }
}
