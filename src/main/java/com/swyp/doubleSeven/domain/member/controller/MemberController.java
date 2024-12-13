package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.common.aspect.AuthenticationUtil;
import com.swyp.doubleSeven.common.aspect.anotation.AuthCheck;
import com.swyp.doubleSeven.common.exception.BusinessException;
import com.swyp.doubleSeven.common.util.CommonAspect;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.common.enums.Error;
import com.swyp.doubleSeven.domain.common.enums.Role;
import com.swyp.doubleSeven.domain.member.dto.request.MemberRequest;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final CommonAspect commonAspect;
    private final AuthenticationUtil authenticationUtil;

    @GetMapping("/auth/kakao-login")
    public ResponseEntity<MemberResponse> kakaoLogin(@RequestParam("code") String memberKeyId, HttpServletResponse response) {

        MemberResponse memberResponse = memberService.processKakaoUser(memberKeyId);

        List<BadgeResponse> badgeResponseList = commonAspect.afterLogin(memberResponse.getMemberId().intValue());
        memberResponse.setBadgeResponseList(badgeResponseList);

        // 쿠키 설정을 위한 공통 속성 (Domain 유지)
        String cookieProperties = "Path=/; SameSite=None; Secure; HttpOnly; Max-Age=2592000";
        // 각 쿠키 설정
        response.addHeader("Set-Cookie", String.format("memberKeyId=%s; %s",
                memberResponse.getMemberKeyId(), cookieProperties));
        response.addHeader("Set-Cookie", String.format("memberId=%s; %s",
                memberResponse.getMemberId().toString(), cookieProperties));
        response.addHeader("Set-Cookie", String.format("loginType=%s; %s",
                "KAKAO", cookieProperties));

        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping("/users")
    @Operation
    @AuthCheck(allowedRoles = Role.MEMBER)
    public ResponseEntity<MemberResponse> updateMemberNickname(@RequestBody MemberRequest memberRequest) {
        MemberResponse memberResponse = memberService.updateMemberNickname(memberRequest);
        return ResponseEntity.ok().body(memberResponse);
    }

    @PostMapping("/withdrawal")
    @AuthCheck(allowedRoles = Role.MEMBER)
    public int withdraw(@RequestParam("memberId") Integer memberId) {
        Integer currentMemberId = authenticationUtil.getCurrentMemberId();
        if(currentMemberId != memberId) {
            throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
        }
        return memberService.withdrawMember(currentMemberId);
    }
}
