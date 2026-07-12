package com.example._teamcommerce.auth.exception;

import lombok.Getter;
import org.springframework.web.bind.annotation.PutMapping;

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
