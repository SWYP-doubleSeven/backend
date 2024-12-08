package com.swyp.doubleSeven.domain.common.category.service;

import com.swyp.doubleSeven.domain.common.category.dto.response.SubCategoryResponse;
import com.swyp.doubleSeven.domain.common.category.dto.response.CategoryOrderResponse;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;

import java.util.List;

public interface CategoryService {

    // 하위 카테고리명 조회
    SubCategoryResponse getSubCategoryNames ();

    // 카테고리 정렬
    List<CategoryOrderResponse> countByCategory(Integer memberId, int year, int month);

    // 많이 사용한 카테고리 순으로 정렬
    //int incrementCategoryCount(SavingRequest savingRequest);
}
