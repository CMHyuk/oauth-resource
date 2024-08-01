package com.oauth.resource.domain.user.config;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    private static final SCryptPasswordEncoder passwordEncoder = new SCryptPasswordEncoder(
            16,
            8,
            1,
            32,
            64
    );

    public String encrypt(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public void match(String rawPassword, String encodedPassword) {
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        if (!matches) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }
}
