package com.example._teamcommerce.admin.service;

import com.example._teamcommerce.admin.dto.request.AdminSignupRequest;
import com.example._teamcommerce.admin.dto.response.AdminSignupResponse;
import com.example._teamcommerce.admin.entity.Admin;
import com.example._teamcommerce.admin.repository.AdminRepository;
import com.example._teamcommerce.common.config.PasswordEncoder;
import com.example._teamcommerce.common.exception.BusinessException;
import com.example._teamcommerce.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSignupService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // 관리자 회원가입
    @Transactional
    public AdminSignupResponse signup(AdminSignupRequest request) {

        // 1. 이미 가입된 이메일인지 확인
        validateDuplicateEmail(request.getEmail());

        // 2. 사용자가 입력한 원본 비밀번호를 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. 관리자(Admin) 객체 생성 (상태(status)는 생성자에서 자동으로 PENDING)
        Admin admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .phone(request.getPhone())
                .role(request.getRole())
                .build();

        // 4. 생성한 관리자 정보를 DB에 저장
        Admin savedAdmin = adminRepository.save(admin);

        // 5. 저장된 Admin Entity를 Response DTO로 변환하여 반환
        return AdminSignupResponse.from(savedAdmin);

    }

    // 이메일 중복 검사
    private void validateDuplicateEmail(String email) {

        if (adminRepository.existsByEmail(email)) {
            throw new BusinessException(
                    ErrorCode.ADMIN_EMAIL_DUPLICATED
            );
        }
    }
}
