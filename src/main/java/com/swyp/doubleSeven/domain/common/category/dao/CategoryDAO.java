package com.swyp.doubleSeven.domain.common.category.dao;

import com.swyp.doubleSeven.domain.common.category.dto.response.CategoryOrderResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDAO {

    // 하위 카테고리명 조회
    List<String> selectSubCategoryNames ();

    // 카테고리 정렬
    List<CategoryOrderResponse> selectMonthlyCategoryOrder(
            Integer memberId,
            int year,
            int month
    );
}
