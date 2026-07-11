package com.example._teamcommerce.auth.controller;

import com.example._teamcommerce.auth.dto.request.LoginAdminRequest;
import com.example._teamcommerce.auth.dto.request.SignupAdminRequest;
import com.example._teamcommerce.auth.dto.response.SignupAdminResponse;
import com.example._teamcommerce.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class AuthController {

    private final AuthService authService;
    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupAdminResponse> signup(@Valid @RequestBody SignupAdminRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginAdminRequest request,
                                      HttpServletRequest servletRequest){

        // 이메일과 일치하는 관리자id 반환
        Long id = authService.login(request);

        // 세션 생성
        HttpSession session = servletRequest.getSession();

        // 세션에 로그인한 관리자id 저장
        session.setAttribute("LOGIN_ADMIN", id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest servletRequest) {

        // 기존 세션 가져오기, 없으면 null
        HttpSession session = servletRequest.getSession(false);

        // 세션이 존재하면 무효화
        if ( session != null) session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
