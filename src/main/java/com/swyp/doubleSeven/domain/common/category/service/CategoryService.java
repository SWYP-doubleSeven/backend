package com.swyp.doubleSeven.domain.common.category.service;

import com.swyp.doubleSeven.domain.common.category.dto.response.SubCategoryResponse;

public interface CategoryService {

    // 하위 카테고리명 조회
    SubCategoryResponse getSubCategoryNames ();
}
