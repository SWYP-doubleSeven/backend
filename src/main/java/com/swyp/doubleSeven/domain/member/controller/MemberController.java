package com.swyp.doubleSeven.domain.member.controller;

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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/auth/kakao-login")
    public ResponseEntity<MemberResponse> kakaoLogin(@RequestParam("code") String code, HttpSession session) {

//      String accessToken = memberService.getKakaoAccessToken(code, httpServletRequest);
        MemberResponse memberResponse = memberService.processKakaoUser(code);

        session.setAttribute("memberId", memberResponse.getMemberId());
        session.setAttribute("memberNickname", memberResponse.getMemberNickname());
        session.setAttribute("loginType", memberResponse.getLoginType());
        session.setAttribute("role", memberResponse.getRole());

        List<BadgeResponse> badgeResponseList = commonAspect.afterLogin(memberResponse.getMemberId().intValue());
        memberResponse.setBadgeResponseList(badgeResponseList);

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
    public int withdraw(@RequestParam("memberId") Integer memberId, HttpSession session) {
        if((Integer)session.getAttribute("memberId") != memberId) {
            throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
        }
        return memberService.withdrawMember(memberId);
    }
}
