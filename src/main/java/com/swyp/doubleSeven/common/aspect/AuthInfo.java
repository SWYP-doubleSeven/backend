package com.swyp.doubleSeven.common.aspect;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthInfo {

    private Integer memberId;

    private String loginType;

    private String role;
}
