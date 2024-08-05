package com.oauth.resource.domain.client.dto;

import com.oauth.resource.domain.client.model.ClientInfo;

import java.util.Set;

public record MasterClientInfoResponse(String clientId, String clientSecret, Set<String> redirectUris, Set<String> scopes) {

    public static MasterClientInfoResponse from(ClientInfo clientInfo) {
        return new MasterClientInfoResponse(
                clientInfo.getClientId(),
                clientInfo.getClientSecret(),
                clientInfo.getRegisteredRedirectUris(),
                clientInfo.getScopes());
    }
}
