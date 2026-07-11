package com.example._teamcommerce.auth.service;

import com.example._teamcommerce.admin.entity.Admin;
import com.example._teamcommerce.admin.repository.AdminRepository;
import com.example._teamcommerce.auth.dto.request.LoginAdminRequest;
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
        Admin admin = new Admin(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getPassword());

        // 객체 저장
        Admin savedAdmin = adminRepository.save(admin);

        // 객체 반환
        return new SignupAdminResponse(
                savedAdmin.getId(),
                savedAdmin.getName(),
                savedAdmin.getEmail(),
                savedAdmin.getPhone(),
                savedAdmin.getStatus(),
                savedAdmin.getCreatedAt());

    }

    // 로그인
    @Transactional
    public Long login(LoginAdminRequest request) {

        // admin 조회
        Admin admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않은 관리자입니다."));

        // 비밀번호 확인
        if (!admin.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치 하지 않습니다.");
        }

        // 로그인 성공 반환
        return admin.getId();

    }


}
