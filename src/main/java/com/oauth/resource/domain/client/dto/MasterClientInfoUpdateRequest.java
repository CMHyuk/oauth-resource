package com.oauth.resource.domain.client.dto;

import java.util.List;

public record MasterClientInfoUpdateRequest(
        String clientName,
        String clientId,
        String clientSecret,
        String registeredRedirectUri,
        Integer accessTokenValiditySeconds,
        List<String> scopes
) {
}
