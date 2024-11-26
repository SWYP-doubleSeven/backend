package com.swyp.doubleSeven.domain.common.category.service;

import com.swyp.doubleSeven.domain.common.category.dao.CategoryDAO;
import com.swyp.doubleSeven.domain.common.category.dto.response.subCategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    private final CategoryDAO categoryDAO;

    // 하위 카테고리명 조회
    @Override
    public subCategoryResponse getSubCategoryNames () {
        List<String> subCategoryNames = categoryDAO.selectSubCategoryNames();

        return subCategoryResponse.builder()
                .categoryNames(subCategoryNames)
                .build();
    }
}
