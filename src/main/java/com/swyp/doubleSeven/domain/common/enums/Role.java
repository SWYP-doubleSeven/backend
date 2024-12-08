package com.swyp.doubleSeven.domain.common.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN", "관리자"),
    MEMBER("MEMBER", "멤버"),
    GUEST("GUEST", "게스트");

    private final String type;
    private final String meaning;

    Role(String type, String meaning) {
        this.type = type;
        this.meaning = meaning;
    }
}
