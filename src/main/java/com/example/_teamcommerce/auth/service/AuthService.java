package com.example._teamcommerce.auth.service;

import com.example._teamcommerce.admin.entity.Admin;
import com.example._teamcommerce.admin.repository.AdminRepository;
import com.example._teamcommerce.auth.dto.request.SignupAdminRequest;
import com.example._teamcommerce.auth.dto.response.SignupAdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class AuthService {

    // 회원가입 = admin 생성
    private final AdminRepository adminRepository;

    // 회원가입
    @Transactional
    public SignupAdminResponse signup(SignupAdminRequest request) {

        // 이메일 중복확인
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("다른 관리자가 사용 중인 이메일 입니다.");
        }

        // admin 객체 생성
        Admin admin = new Admin()
    }


}
