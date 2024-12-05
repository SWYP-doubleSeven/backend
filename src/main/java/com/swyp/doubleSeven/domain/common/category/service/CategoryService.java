package com.swyp.doubleSeven.domain.common.category.service;

import com.swyp.doubleSeven.domain.common.category.dto.response.SubCategoryResponse;
import com.swyp.doubleSeven.domain.common.category.dto.response.CategoryOrderResponse;

import java.util.List;

public interface CategoryService {

    // 하위 카테고리명 조회
    SubCategoryResponse getSubCategoryNames ();

    // 카테고리 정렬
    List<CategoryOrderResponse> getMonthlyCategoryRank(Integer memberId, int year, int month);
}
