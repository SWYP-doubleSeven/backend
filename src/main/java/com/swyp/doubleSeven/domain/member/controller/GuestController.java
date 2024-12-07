package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.common.aspect.AuthenticationUtil;
import com.swyp.doubleSeven.domain.member.dto.response.guest.GuestLoginResponse;
import com.swyp.doubleSeven.domain.member.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
@Tag(name = "Guest", description = "게스트 관련 API")
public class GuestController {

    private final GuestService guestService;

    private final AuthenticationUtil authenticationUtil;

    // 게스트 로그인/재로그인
    @Operation(summary = "게스트 로그인", description = "최초 게스트 로그인 및 재로그인을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            //@ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<GuestLoginResponse> signInGuest (
            HttpServletRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok(guestService.signInGuest(request, response));
    }

    @Operation(summary = "API 통신 X", description = "통신이 필요하지 않은 ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            //@ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    //@DeleteMapping("/expired")
    public ResponseEntity<Void> deleteExpiredGuestData () {
        guestService.deleteExpiredGuestData ();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "소셜 로그인 연동 유도 체크", description = "글작성 2회마다 true를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            //@ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    //@ValidateOnExecution
    @GetMapping("/show-modal")
    public ResponseEntity<Boolean> countSaving () {
        Integer currentMemberId = authenticationUtil.getCurrentMemberId();
        return ResponseEntity.ok(guestService.countSaving(currentMemberId));
    }
}
