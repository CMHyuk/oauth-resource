package com.oauth.resource.support;

import lombok.Getter;
import lombok.Setter;

public class TokenContext {

    @Getter @Setter
    private static String accessToken;

    public static String getBearerToken() {
        return "Bearer " + accessToken;
    }
}
