package com.swyp.doubleSeven.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode; // 사용자 정의 에러코드
    private String message; // 클라이언트에 전달할 에러 메세지
}
