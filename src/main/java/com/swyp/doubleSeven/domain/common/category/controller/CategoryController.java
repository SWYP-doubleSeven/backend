package com.swyp.doubleSeven.domain.common.category.controller;

import com.swyp.doubleSeven.domain.common.category.dto.response.subCategoryResponse;
import com.swyp.doubleSeven.domain.common.category.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    // 하위 카테고리명 조회
    @GetMapping("/names")
    public ResponseEntity<subCategoryResponse> getSubCategoryNames () {
        return ResponseEntity.ok(categoryServiceImpl.getSubCategoryNames());
    }
}
