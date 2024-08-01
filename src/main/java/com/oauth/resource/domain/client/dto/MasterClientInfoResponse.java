package com.oauth.resource.domain.client.dto;

import com.oauth.resource.domain.client.model.ClientInfo;

import java.util.List;

public record MasterClientInfoResponse(String clientId, String clientSecret, String redirectUri, List<String> scopes) {

    public static MasterClientInfoResponse from(ClientInfo clientInfo) {
        return new MasterClientInfoResponse(
                clientInfo.getClientId(),
                clientInfo.getClientSecret(),
                clientInfo.getRegisteredRedirectUri(),
                clientInfo.getScope());
    }
}
