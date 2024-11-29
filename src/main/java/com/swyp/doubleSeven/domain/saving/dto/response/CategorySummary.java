package com.swyp.doubleSeven.domain.saving.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummary {

    private String categoryName; // 카테고리명

    private long categoryTotalAmount; // 카테고리 총합

}
