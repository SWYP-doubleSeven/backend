package com.swyp.doubleSeven.domain.member.controller;

import com.swyp.doubleSeven.domain.member.login.LoginForm;
import com.swyp.doubleSeven.domain.member.login.LoginServiceImpl;
import com.swyp.doubleSeven.domain.member.login.socialLogin.GoogleOauth;
import com.swyp.doubleSeven.domain.member.login.socialType.SocialLoginType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceImpl oAuthService;

//    @GetMapping("api/login")
//    public String loginForm(@ModelAttribute LoginForm loginForm) {
//        return null; // 추후 프론트단 화면에 연결할 예정
//    }


    @GetMapping("/auth/{socialLoginType}") //GOOGLE이 들어올 것이다.
    public void socialLoginRedirect(@PathVariable(name="socialLoginType") String SocialLoginPath) throws IOException {
        SocialLoginType socialLoginType= SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        oAuthService.request(socialLoginType);
    }

    @GetMapping("/login/oauth2/code/google")
    public void callback(
            //PathVariable(name = "socialLoginType")String socialLoginPath,
            @RequestParam(name = "code") String code
    ) throws IOException {
        //SocialLoginType socialLoginType = SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        oAuthService.oauthLogin(SocialLoginType.GOOGLE, code);
    }






//    /**
//     * Social Login API Server 요청에 의한 callback 을 처리
//     * @param socialLoginPath (GOOGLE, KAKAO)
//     * @param code API Server 로부터 넘어오는 code
//     * @return SNS Login 요청 결과로 받은 Json 형태의 java 객체 (access_token, jwt_token, user_num 등)
//     */
//    @ResponseBody
//    @GetMapping(value = "/auth/{socialLoginType}/callback")
//    public BaseResponse<GetSocialOAuthRes> callback (
//            @PathVariable(name = "socialLoginType") String socialLoginPath,
//            @RequestParam(name = "code") String code)throws IOException,BaseException{
//        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);
//        SocialLoginType socialLoginType= SocialLoginType.valueOf(socialLoginPath.toUpperCase());
//        GetSocialOAuthRes getSocialOAuthRes=oAuthService.oAuthLogin(socialLoginType,code);
//        return new BaseResponse<>(getSocialOAuthRes);
//    }


}
