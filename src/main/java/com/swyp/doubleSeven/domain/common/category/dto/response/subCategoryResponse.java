package com.swyp.doubleSeven.domain.common.category.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class subCategoryResponse {

    private List<String> categoryNames; // 하위 카테고리명 리스트
}
