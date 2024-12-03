package com.swyp.doubleSeven.domain.common.enums;

import com.swyp.doubleSeven.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum Error implements ErrorCode {
    INTERVAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 입니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),

    RESOURCE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 리소스에 대한 권한이 없습니다.");

    private final HttpStatus status;

    private String message;

    Error(HttpStatus status, String message) {
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
