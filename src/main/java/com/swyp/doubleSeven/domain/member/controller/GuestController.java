package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.common.aspect.AuthenticationUtil;
import com.swyp.doubleSeven.domain.common.enums.LoginType;
import com.swyp.doubleSeven.domain.common.enums.Role;
import com.swyp.doubleSeven.domain.member.dto.response.guest.GuestLoginResponse;
import com.swyp.doubleSeven.domain.member.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<GuestLoginResponse> signInGuest(HttpServletRequest request, HttpServletResponse response) {
        GuestLoginResponse guestResponse = guestService.signInGuest(request/*, response*/);

        // 쿠키 설정을 위한 공통 속성 (Domain 유지)
        String cookieProperties = "Path=/; SameSite=None; Secure; HttpOnly; Max-Age=2592000";
        //String cookieProperties = "Path=/; Domain=api-zerocost.site; Secure; SameSite=None; Max-Age=2592000; Partitioned";

        // 각 쿠키 설정
        response.addHeader("Set-Cookie", String.format("memberKeyId=%s; %s",
                guestResponse.getMemberKeyId(), cookieProperties));
        response.addHeader("Set-Cookie", String.format("memberId=%s; %s",
                guestResponse.getMemberId().toString(), cookieProperties));
        response.addHeader("Set-Cookie", String.format("loginType=%s; %s",
                "GUEST", cookieProperties));

        /*// memberKeyId 쿠키 설정
        String memberKeyIdCookie = String.format("%s=%s; %s",
                "memberKeyId",
                guestResponse.getMemberKeyId(),
                cookieProperties
        );
        response.addHeader("Set-Cookie", memberKeyIdCookie);

        // memberId 쿠키 설정
        String memberIdCookie = String.format("%s=%s; %s",
                "memberId",
                guestResponse.getMemberId().toString(),
                cookieProperties
        );
        response.addHeader("Set-Cookie", memberIdCookie);

        // loginType 쿠키 설정
        String loginTypeCookie = String.format("%s=%s; %s",
                "loginType",
                "GUEST",
                cookieProperties
        );
        response.addHeader("Set-Cookie", loginTypeCookie);*/

        return ResponseEntity.ok(guestResponse);
    }

        /*// 쿠키 설정을 헤더로 직접
        String cookieValue = String.format("%s=%s; Path=/; Domain=api-zerocost.site; Secure; SameSite=None",
                "memberKeyId", guestResponse.getMemberKeyId());
        response.addHeader("Set-Cookie", cookieValue);

        String memberIdCookieValue = String.format("%s=%s; Path=/; Domain=api-zerocost.site; Secure; SameSite=None",
                "memberId", guestResponse.getMemberId().toString());
        response.addHeader("Set-Cookie", memberIdCookieValue);

        String loginTypeCookieValue = String.format("%s=%s; Path=/; Domain=api-zerocost.site; Secure; SameSite=None",
                "loginType", "GUEST");
        response.addHeader("Set-Cookie", loginTypeCookieValue);

        return ResponseEntity.ok(guestResponse);
    }*/
    /*public ResponseEntity<GuestLoginResponse> signInGuest (
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
            ) {
        GuestLoginResponse guestLoginResponse = guestService.signInGuest(request, response);
        session.setAttribute("memberId", guestLoginResponse.getMemberId());
        session.setAttribute("memberNickname", guestLoginResponse.getMemberNickname());
        session.setAttribute("loginType", LoginType.GUEST.getType());
        session.setAttribute("role", Role.GUEST.getType());

        return ResponseEntity.ok(guestService.signInGuest(request, response));
    }*/

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
