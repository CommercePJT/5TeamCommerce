package com.example._teamcommerce.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ADMIN_EMAIL_DUPLICATED(
            HttpStatus.CONFLICT,
            "ADMIN_EMAIL_DUPLICATED",
            "이미 사용중인 이메일입니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;

}

// HTTP 상태: 409 Conflict
//오류 코드: ADMIN_EMAIL_DUPLICATED
//메시지: 이미 사용 중인 이메일입니다.
