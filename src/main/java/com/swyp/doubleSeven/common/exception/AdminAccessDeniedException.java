package com.swyp.doubleSeven.common.exception;

import lombok.Getter;

@Getter
public class AdminAccessDeniedException extends RuntimeException{
    private final String errorCode;

    public AdminAccessDeniedException(String message) {
        super(message);
        this.errorCode = "ADMIN_ACCESS_DENIED";
    }
}
