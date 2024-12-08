package com.swyp.doubleSeven.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.doubleSeven.common.aspect.anotation.AuthCheck;
import com.swyp.doubleSeven.common.util.CommonAspect;
import com.swyp.doubleSeven.common.util.CommonImageUploader;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.common.enums.Role;
import com.swyp.doubleSeven.domain.member.dto.request.MemberRequest;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final CommonAspect commonAspect;
    private final CommonImageUploader commonImageUploader;

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

//    @PostMapping("/users")
//    @Operation
//    @AuthCheck(allowedRoles = Role.MEMBER)
//    public ResponseEntity<MemberResponse> updateMemberInfo(
//            @RequestPart(value = "memberRequest") String memberRequestJson,
//            @RequestPart(value = "profileImage", required = false) MultipartFile multipartFile
//    ) {
//        // JSON 파싱
//        ObjectMapper objectMapper = new ObjectMapper();
//        MemberRequest memberRequest;
//        try {
//            memberRequest = objectMapper.readValue(memberRequestJson, MemberRequest.class);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        String fileName = "";
//        if(multipartFile != null && !multipartFile.isEmpty()) {
//            try {
//                fileName = commonImageUploader.upload(multipartFile, "profile_image");
//                memberRequest.set
//            } catch (IOException e) {
//                e.printStackTrace();
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(null); // 업로드 실패 처리
//            }
//        }
//
//        MemberResponse memberResponse = memberService.updateMemberInfo(memberRequest);
//        return ResponseEntity.ok().body(memberResponse);
//    }
}
