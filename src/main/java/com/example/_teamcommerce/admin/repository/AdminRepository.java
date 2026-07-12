package com.example._teamcommerce.admin.repository;

import com.example._teamcommerce.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByEmail(String email);
                                  // 같은 이메일을 가진 관리자가 존재하는지 확인
                                  // SELECT ... WHERE email = ?
}
