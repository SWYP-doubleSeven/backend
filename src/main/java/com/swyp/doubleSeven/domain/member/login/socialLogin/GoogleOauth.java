package com.swyp.doubleSeven.domain.member.login.socialLogin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequiredArgsConstructor
public class GoogleOauth implements SocialOauth{

    @Override
    public String getOauthRedirectUrl() {
        return "";
    }
}
