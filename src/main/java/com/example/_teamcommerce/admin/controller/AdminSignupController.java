package com.example._teamcommerce.admin.controller;

import com.example._teamcommerce.admin.dto.request.AdminSignupRequest;
import com.example._teamcommerce.admin.dto.response.AdminSignupResponse;

import com.example._teamcommerce.admin.service.AdminSignupService;
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
@RequestMapping("/admins")
public class AdminSignupController {

    private final AdminSignupService adminSignupService;

    // 관리자 회원가입 (가입 신청 -> 승인 대기 상태로 생성됌)
    @PostMapping("/signup")
    public ResponseEntity<AdminSignupResponse> signup(
            @Valid @RequestBody AdminSignupRequest request
    ) {
        AdminSignupResponse response = adminSignupService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)          // 201 C
                .body(response);
    }
}
