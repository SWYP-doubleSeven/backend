package com.swyp.doubleSeven.domain.common.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 정렬 순서 정보")
public class CategoryOrderResponse {

    @Schema(description = "카테고리명", example = "coffee")
    private String categoryName;

    @Schema(description = "카테고리 사용 횟수", example = "5")
    private long categoryCount;
}
