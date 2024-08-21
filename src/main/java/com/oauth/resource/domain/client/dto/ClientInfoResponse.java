package com.oauth.resource.domain.client.dto;

import com.oauth.resource.domain.client.model.ClientInfo;

import java.util.Set;

public record ClientInfoResponse(String clientId, String clientSecret, Set<String> redirectUris, Set<String> scopes) {

    public static ClientInfoResponse from(ClientInfo clientInfo) {
        return new ClientInfoResponse(
                clientInfo.getClientId(),
                clientInfo.getClientSecret(),
                clientInfo.getRegisteredRedirectUris(),
                clientInfo.getScopes());
    }
}
