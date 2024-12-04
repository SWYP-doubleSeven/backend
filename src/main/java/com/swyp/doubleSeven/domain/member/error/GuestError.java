package com.swyp.doubleSeven.domain.member.error;

import com.swyp.doubleSeven.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum GuestError implements ErrorCode {

    NOTFOUND_GUEST(HttpStatus.NOT_FOUND, "유효하지 않은 게스트 사용자입니다."),

    GUEST_ACCESS_DENIED(HttpStatus.FORBIDDEN, "게스트는 접근할 수 없는 기능입니다.");
    ;


    private final HttpStatus status;
    private final String message;

    GuestError(HttpStatus status, String message) {
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
