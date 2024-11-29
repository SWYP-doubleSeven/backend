package com.swyp.doubleSeven.domain.common.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "카테고리명 응답")
public class SubCategoryResponse {

    @Schema(
            description = "카테고리명 목록",
            example = "[\"coffee\", \"dessert\", \"travel\", \"meal\", \"smoke\"]"
    )
    private List<String> categoryNames; // 하위 카테고리명 리스트
}
