package com.oauth.resource.global.util;

import com.oauth.resource.global.exception.BusinessException;
import com.oauth.resource.global.exception.InternalServerErrorCode;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyPairProvider {

    private static final String ALGORITHM = "RSA";

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance(ALGORITHM);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw BusinessException.from(new InternalServerErrorCode(e.getMessage()));
        }
    }
}
