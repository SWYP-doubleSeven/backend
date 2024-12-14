package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.common.aspect.AuthenticationUtil;
import com.swyp.doubleSeven.common.aspect.anotation.VaildateResourceOwner;
import com.swyp.doubleSeven.domain.member.dto.response.MemberStatusResponse;
import com.swyp.doubleSeven.domain.member.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/personal")
@Tag(name = "MyPage", description = "마이페이지 API")
public class MyPageController {

    private final MyPageService myPageService;

    private final AuthenticationUtil authenticationUtil;

    @Operation(
            summary = "회원 상태 조회",
            description = "[ 제로코스트와 함께 한 N일 동안 N원을 절약했어요. ] 해당 문구에 응답합니다.",
            security = {@SecurityRequirement(name = "cookieAuth")}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MemberStatusResponse.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                        "daysFromRegistration": 5,
                        "totalAmount": 18000
                    }
                    """
                            )
                    )
            )
    })
    @SecurityRequirement(name = "cookieAuth")
    @GetMapping("/member-status")
    public MemberStatusResponse selectMemberStatus () {
//        Integer currentMemberId = authenticationUtil.getCurrentMemberId();
        Integer currentMemberId = 45;
        return myPageService.selectMemberStatus(currentMemberId);
    }
}
