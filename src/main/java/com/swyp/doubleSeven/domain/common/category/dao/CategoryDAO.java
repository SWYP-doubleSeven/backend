package com.swyp.doubleSeven.domain.common.category.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDAO {

    // 하위 카테고리명 조회
    List<String> selectSubCategoryNames ();
}
