package com.swyp.doubleSeven.domain.saving.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@Schema(description = "가상 소비 응답")
public class SavingResponse {

    @Schema(description = "멤버 ID", example = "1")
    private Integer memberId;

    @Schema(description = "가상 소비 ID", example = "1")
    private Integer savingId;

    @Schema(description = "가상 소비 일자", example = "2024-03-27")
    private LocalDate savingYmd;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalTime savingTime;

    @Schema(description = "가상 소비 금액", example = "10000")
    private int amount;

    @Schema(description = "카테고리명", example = "meal")
    private String categoryName;

    @Builder
    public SavingResponse(Integer memberId, Integer savingId, LocalDate savingYmd, LocalTime savingTime, int amount, String categoryName) {
        this.memberId = memberId;
        this.savingId = savingId;
        this.savingYmd = savingYmd;
        this.savingTime = savingTime;
        this.amount = amount;
        this.categoryName = categoryName;
    }
}
