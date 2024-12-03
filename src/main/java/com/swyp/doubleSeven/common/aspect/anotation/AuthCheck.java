package com.swyp.doubleSeven.common.aspect.anotation;

import com.swyp.doubleSeven.domain.common.enums.LoginType;
import com.swyp.doubleSeven.domain.common.enums.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    // 허용되는 로그인 타입
    LoginType[] allowedTypes () default {LoginType.KAKAO, LoginType.GUEST};

    // 허용되는 역할
    Role[] allowedRoles () default {Role.ADMIN, Role.MEMBER, Role.GUEST};

    // 로그인한 ID == 데이터 작성자 ID
    boolean validateAuthor () default false;
}
