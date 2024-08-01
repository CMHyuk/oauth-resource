package com.oauth.resource.domain.user.config;

import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.global.exception.BusinessException;
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
            throw BusinessException.from(UserErrorCode.LOGIN_FAILED);
        }
    }
}
