package com.example._teamcommerce.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice          // 프로젝트 전체에서 발생하는 예외 감시
public class GlobalExceptionHandler {

    // Service에서 직접 발생시킨 BusinessException을 잡아서 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new ErrorResponse(errorCode));
    }

    // @Valid 검증 실패 시 발생하는 예외를 잡아서 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {

        // 검증에 실패한 항목 중 첫 번째의 메시지를 꺼냄
        String message = ErrorCode.INVALID_INPUT.getMessage();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            message = fieldError.getDefaultMessage();
            break;
        }

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getStatus())                       // 400
                .body(new ErrorResponse(ErrorCode.INVALID_INPUT.getCode(), message));
    }
}


