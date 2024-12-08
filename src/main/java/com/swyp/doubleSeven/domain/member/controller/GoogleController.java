package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.common.util.CommonAspect;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.common.enums.LoginType;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.service.GoogleLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GoogleController {

    private final GoogleLoginService googleLoginService;
    private final CommonAspect commonAspect;

    @GetMapping("/google-login") //이걸 void 로 하고 밑에 메서드 리턴을 차라리 ResponseEntity<MemberResponse> 로 해야할듯
    public void googleLogin(HttpSession session,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {//추후에 리퀘파람 code 는 유연하게 주석 할수도
        googleLoginService.request(LoginType.GOOGLE,response);
        log.info("is");

    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<MemberResponse> callback(
            @RequestParam(name = "code") String code,
            HttpSession session
    ) throws IOException { //인가 코드 날라옴
        MemberResponse memberResponse = googleLoginService.oauthLogin(LoginType.GOOGLE, code);
        session.setAttribute("memberId", memberResponse.getMemberId());
        session.setAttribute("memberNickname", memberResponse.getMemberNickname());
        session.setAttribute("loginType", memberResponse.getLoginType());
        session.setAttribute("role", memberResponse.getRole());

        List<BadgeResponse> badgeResponseList = commonAspect.afterLogin(memberResponse.getMemberId().intValue());
        memberResponse.setBadgeResponseList(badgeResponseList);

        log.info("로그인 처리 완료 ");



        return ResponseEntity.ok(memberResponse);

    }
}
