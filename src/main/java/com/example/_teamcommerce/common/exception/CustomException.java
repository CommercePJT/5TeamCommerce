package com.example._teamcommerce.common.exception;

import lombok.Getter;

@Getter

public class CustomException extends RuntimeException {

    //  발생한 에러 정보
    private final ErrorCode errorCode;

    // Error Code 받은걸로 예외 생성
    public CustomException(ErrorCode errorCode) {

        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
