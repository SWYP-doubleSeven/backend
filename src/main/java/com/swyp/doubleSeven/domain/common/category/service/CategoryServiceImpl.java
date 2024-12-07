package com.swyp.doubleSeven.domain.common.category.service;

import com.swyp.doubleSeven.common.exception.BusinessException;
import com.swyp.doubleSeven.domain.common.category.dao.CategoryDAO;
import com.swyp.doubleSeven.domain.common.category.dto.response.SubCategoryResponse;
import com.swyp.doubleSeven.domain.common.category.dto.response.CategoryOrderResponse;
import com.swyp.doubleSeven.domain.common.category.error.CategoryError;
import com.swyp.doubleSeven.domain.common.enums.Error;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    private final CategoryDAO categoryDAO;

    // 하위 카테고리명 조회
    @Override
    public SubCategoryResponse getSubCategoryNames () {
        List<String> subCategoryNames = categoryDAO.selectSubCategoryNames();

        return SubCategoryResponse.builder()
                .categoryNames(subCategoryNames)
                .build();
    }

    // 카테고리 정렬
    @Override
    public List<CategoryOrderResponse> countByCategory(Integer memberId, int year, int month) {
        return categoryDAO.countByCategory(memberId, year, month);
    }


}
