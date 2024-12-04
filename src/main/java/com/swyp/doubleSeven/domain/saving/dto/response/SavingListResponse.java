package com.swyp.doubleSeven.domain.saving.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@Schema(description = "가상 소비 목록 응답")
public class SavingListResponse {

    @Schema(description = "멤버 ID", example = "1")
    private Integer memberId;

    @Schema(description = "가상 소비 목록")
    private List<SavingResponse> items;

    @Schema(description = "총 소비 금액", example = "150000")
    private long totalAmount;

    @Builder
    public SavingListResponse(Integer memberId, List<SavingResponse> items, long totalAmount) {
        this.memberId = memberId;
        this.items = items;
        this.totalAmount = totalAmount;
    }
}
