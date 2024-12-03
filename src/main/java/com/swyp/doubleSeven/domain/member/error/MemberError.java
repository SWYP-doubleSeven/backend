package com.swyp.doubleSeven.domain.member.error;

import com.swyp.doubleSeven.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberError implements ErrorCode {

    LOGINTYPE_ACCESS_DENIED (HttpStatus.UNAUTHORIZED, "허용되지 않은 로그인 타입입니다."),

    MEMBER_ACCESS_DENIED (HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
    ;


    private final HttpStatus status;
    private final String message;

    MemberError (HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
