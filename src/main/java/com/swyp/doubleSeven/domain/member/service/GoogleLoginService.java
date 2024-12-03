package com.swyp.doubleSeven.domain.member.service;

import com.swyp.doubleSeven.domain.common.enums.LoginType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {

    private final GoogleOauth  googleOauth;

    public void request(LoginType socialLoginType, HttpServletResponse response) throws IOException {
        //매개변수의 Resposne 그냥 필드에 박하 넣을수도 있음


        String redirectURL;
        redirectURL = googleOauth.getOauthRedirectUrl();
//            }break;
//            default:{
//                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
//            }

        response.sendRedirect(redirectURL);
    }
}
