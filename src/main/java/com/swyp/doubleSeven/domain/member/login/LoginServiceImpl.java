package com.swyp.doubleSeven.domain.member.login;

import com.swyp.doubleSeven.domain.member.Member;
import com.swyp.doubleSeven.domain.member.login.socialLogin.GoogleOauth;
import com.swyp.doubleSeven.domain.member.login.socialType.SocialLoginType;
import com.swyp.doubleSeven.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService { //필드에 MemberService 받아서 Mybatis xml 접근

    private final GoogleOauth googleOauth;
    private final HttpServletResponse response;

    private MemberService memberService;

    @Override
    public Member login(Member member) {
        return null;
    }

    public void request(SocialLoginType socialLoginType) throws IOException {
        String redirectURL;
//        switch (socialLoginType){
//            case GOOGLE:{
//                //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스이다.프로세스이다
        redirectURL = googleOauth.getOauthRedirectUrl();
//            }break;
//            default:{
//                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
//            }

        response.sendRedirect(redirectURL);
    }

}


