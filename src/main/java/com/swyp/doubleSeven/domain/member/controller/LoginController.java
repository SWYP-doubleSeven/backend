package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.domain.member.login.LoginForm;
import com.swyp.doubleSeven.domain.member.login.socialType.SocialLoginType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class LoginController {

//    @GetMapping("api/login")
//    public String loginForm(@ModelAttribute LoginForm loginForm) {
//        return null; // 추후 프론트단 화면에 연결할 예정
//    }


    @GetMapping("/auth/{socialLoginType}") //GOOGLE이 들어올 것이다.
    public void socialLoginRedirect(@PathVariable(name="socialLoginType") String SocialLoginPath) throws IOException {
        SocialLoginType socialLoginType= SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        oAuthService.request(socialLoginType);
    }
}
