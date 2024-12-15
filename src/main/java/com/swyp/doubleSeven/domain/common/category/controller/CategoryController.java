package com.swyp.doubleSeven.domain.common.category.controller;

import com.swyp.doubleSeven.common.aspect.AuthenticationUtil;
import com.swyp.doubleSeven.common.aspect.anotation.VaildateResourceOwner;
import com.swyp.doubleSeven.domain.common.category.dto.response.SubCategoryResponse;
import com.swyp.doubleSeven.domain.common.category.service.CategoryService;
import com.swyp.doubleSeven.domain.common.category.dto.response.CategoryOrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/categories")
@Tag(name = "Category API", description = "카테고리 관련 API")
public class CategoryController {

    private final CategoryService categoryService;

    private final AuthenticationUtil authenticationUtil;

    // 하위 카테고리명 조회
    @Operation(summary = "하위 카테고리명 목록 조회", description = "등록된 모든 하위 카테고리의 이름 목록을 조회합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryOrderResponse.class),
                    examples = @ExampleObject(
                            value = """
            [
                {
                    "categoryName": "meal",
                    "categoryCount": 5
                },
                {
                    "categoryName": "taxi",
                    "categoryCount": 1
                }
            ]
            """
                    )
            )
    )
    @GetMapping("/names")
    public ResponseEntity<SubCategoryResponse> getSubCategoryNames () {
        return ResponseEntity.ok(categoryService.getSubCategoryNames());
    }

    // 카테고리 정렬
    @Operation(summary = "카테고리 정렬", description = "해당월에 많이 사용한 카테고리 순으로 정렬됩니다.",
            security = {@SecurityRequirement(name = "cookieAuth")})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "categoryName": "meal",
                                        "categoryCount": 5
                                      },
                                      {
                                        "categoryName": "taxi",
                                        "categoryCount": 1
                                      }
                                    ]
                            """)))
    })
//    @SecurityRequirement(name = "cookieAuth")
    //@VaildateResourceOwner
    @GetMapping("/order-monthly-rank/{year}/{month}")
    public ResponseEntity<List<CategoryOrderResponse>> getMonthlyCategoryRank (
            @Parameter(description = "조회할 연도 (예: 2024)", in = ParameterIn.PATH) @PathVariable int year,
            @Parameter(description = "조회할 월 (1-12)", in = ParameterIn.PATH) @PathVariable int month,
            @RequestParam("memberId") Integer currentMemberId
    ) {
//        Integer currentMemberId = authenticationUtil.getCurrentMemberId();
        return ResponseEntity.ok(categoryService.countByCategory(currentMemberId, year, month));
    }
}
