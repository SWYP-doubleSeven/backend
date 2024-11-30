package com.swyp.doubleSeven.domain.member.login.socialType;

import lombok.Getter;

@Getter
public enum SocialLoginType {
    GOOGLE("GOOGLE", "google 로그인"),
    KAKAO("KAKAO", "kakao 로그인"),
    GUEST("GUEST", "guest 로그인");

    private final String type;
    private final String meaning;

    SocialLoginType(String type, String meaning) {
        this.type = type;
        this.meaning = meaning;
    }

}
