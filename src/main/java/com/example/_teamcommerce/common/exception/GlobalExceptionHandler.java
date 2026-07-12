package com.example._teamcommerce.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalExceptionHandler {

    // 커스텀익셉션 발생 시 실행
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {

        // 예외에서 에러 정보 가져오기
        ErrorCode errorCode = e.getErrorCode();

        // 에러코드에서 설정된 상태와 메시지 반환
        return ResponseEntity.status(errorCode.getStatus()).body(errorCode.getMessage());

    }
}
