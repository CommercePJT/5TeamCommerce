package com.example._teamcommerce.common.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {                                     // 비번 암호화 (제공된 코드)

    // 1번 회원가입에서 사용하는 메서드
    public String encode(String rawPassword) {
        return BCrypt.withDefaults()
                .hashToString(
                        BCrypt.MIN_COST,
                        rawPassword.toCharArray()
                );
    }

    // 2번에서 사용되는 메서드
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer()
                .verify(
                        rawPassword.toCharArray(),
                        encodedPassword
                );

        return result.verified;
    }
}