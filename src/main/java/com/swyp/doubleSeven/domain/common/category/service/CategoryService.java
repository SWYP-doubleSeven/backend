package com.swyp.doubleSeven.domain.common.category.service;

import com.swyp.doubleSeven.domain.common.category.dto.response.subCategoryResponse;

public interface CategoryService {

    // 하위 카테고리명 조회
    subCategoryResponse getSubCategoryNames ();
}
