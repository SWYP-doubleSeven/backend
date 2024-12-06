package com.swyp.doubleSeven.domain.saving.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "가상 소비 리스트 - 일자 그룹")
public class SavingDailyGroup {

    @Schema(description = "날짜", example = "3")
    private int day;

    @Schema(description = "해당 일자 소비 목록")
    private List<SavingResponse> items;
}
