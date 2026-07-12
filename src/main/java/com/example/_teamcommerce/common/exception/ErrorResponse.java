package com.example._teamcommerce.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    // ErrorCode의 기본 메세지를 사용할 때
    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    // Validation처럼 code와  message를 따로 지정하고 싶을 때 사용
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
