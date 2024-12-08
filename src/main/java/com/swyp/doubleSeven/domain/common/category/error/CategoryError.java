package com.swyp.doubleSeven.domain.common.category.error;

import com.swyp.doubleSeven.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CategoryError implements ErrorCode{

    CATEGORY_COUNT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "카테고리 집계 처리에 실패했습니다.");

    ;

    private final HttpStatus status;
    private final String message;

    CategoryError(HttpStatus status, String message) {
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
