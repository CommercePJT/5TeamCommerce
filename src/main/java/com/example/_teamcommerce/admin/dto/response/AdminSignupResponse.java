package com.example._teamcommerce.admin.dto.response;


import com.example._teamcommerce.admin.entity.Admin;
import com.example._teamcommerce.admin.type.AdminRole;
import com.example._teamcommerce.admin.type.AdminStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
@AllArgsConstructor                        // 모든 필드를 받는 생성자 생성
public class AdminSignupResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final AdminRole role;
    private final AdminStatus status;
    private final LocalDateTime createdAt;

    public static AdminSignupResponse from(Admin admin) {
        return new AdminSignupResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhone(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt()
        );
    }

}
