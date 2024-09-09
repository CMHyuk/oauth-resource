package com.oauth.resource.support;

import lombok.Getter;
import lombok.Setter;

public class TokenContext {

    @Getter @Setter
    private static String masterUserAccessToken;

    @Setter
    private static String adminUserAccessToken;

    @Setter
    private static String userAccessToken;

    public static String getMasterBearerToken() {
        return "Bearer " + masterUserAccessToken;
    }

    public static String getAdminBearerToken() {
        return "Bearer " + adminUserAccessToken;
    }

    public static String getUserBearerAccessToken() {
        return "Bearer " + userAccessToken;
    }
}
