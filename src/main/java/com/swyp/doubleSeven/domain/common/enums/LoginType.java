package com.swyp.doubleSeven.domain.common.enums;

import lombok.Getter;

@Getter
public enum LoginType {
    GOOGLE("GOOGLE", "google 로그인"),
    KAKAO("KAKAO", "kakao 로그인"),
    GUEST("GUEST", "guest 로그인");

    private final String type;
    private final String meaning;

    LoginType(String type, String meaning) {
        this.type = type;
        this.meaning = meaning;
    }

}
