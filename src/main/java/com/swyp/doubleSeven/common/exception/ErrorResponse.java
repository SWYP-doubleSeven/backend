package com.swyp.doubleSeven.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode; // 사용자 정의 에러코드
    private String message; // 클라이언트에 전달할 에러 메세지

    private int status; // 상태코드
    private String statusCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CustomFieldError> errors;

    /*
     * 기존 다원님
     * GlobalExceptionHandler - handleAdminAccessDeniedException(),
     * GlobalExceptionHandler - handleException()
     * 메서드 위한 명시적 Constructor
     * */
    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    // Error
    private ErrorResponse (ErrorCode errorCode) {
        message = errorCode.getMessage();
        status = errorCode.getStatus().value();
        statusCode = errorCode.getCode();
        errors = new ArrayList<>();
    }

    ErrorResponse(ErrorCode errorCode, List<CustomFieldError> errors) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus().value();
        this.statusCode = errorCode.getCode();
        this.errors = errors;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse createBinding(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, CustomFieldError.of(bindingResult));
    }



    // 유효성 검사 중첩 클래스(ex. 금액은 필수 데이터입니다.)
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    static class CustomFieldError {

        private String field;

        private String value;

        private String reson;

        public CustomFieldError(String field, String value, String reson) {
            this.field = field;
            this.value = value;
            this.reson = reson;
        }

        public static List<CustomFieldError> of (BindingResult bindingResult) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new CustomFieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()
                    ))
                    .toList();
        }
    }
}
