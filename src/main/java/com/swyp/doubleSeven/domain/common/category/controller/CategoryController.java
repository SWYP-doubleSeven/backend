package com.swyp.doubleSeven.domain.common.category.controller;

import com.swyp.doubleSeven.domain.common.category.dto.response.SubCategoryResponse;
import com.swyp.doubleSeven.domain.common.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Category API", description = "카테고리 관련 API")
public class CategoryController {

    private final CategoryService categoryService;

    // 하위 카테고리명 조회
    @Operation(summary = "하위 카테고리명 목록 조회", description = "등록된 모든 하위 카테고리의 이름 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SubCategoryResponse.class))
            )
    })
    @GetMapping("/names")
    public ResponseEntity<SubCategoryResponse> getSubCategoryNames () {
        return ResponseEntity.ok(categoryService.getSubCategoryNames());
    }
}
