package com.oauth.resource.support;

import lombok.Setter;

public class TokenContext {

    @Setter
    private static String accessToken;

    public static String getAccessToken() {
        return "Bearer " + accessToken;
    }
}
