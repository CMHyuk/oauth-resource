package com.oauth.resource.domain.client.mapper;

import com.oauth.resource.domain.client.dto.MasterClientInfoSaveRequest;
import com.oauth.resource.domain.client.dto.MasterClientInfoUpdateRequest;
import com.oauth.resource.domain.client.model.ClientInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientInfoMapper {

    private final PasswordEncoder passwordEncoder;

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${client.redirectUrl}")
    private String redirectUrl;

    public ClientInfo createMasterClientInfo(String tenantId, MasterClientInfoSaveRequest request) {
        return new ClientInfo(
                tenantId,
                request.clientName(),
                clientId,
                passwordEncoder.encode(clientSecret),
                redirectUrl,
                request.scopes()
        );
    }

    public void update(ClientInfo clientInfo, MasterClientInfoUpdateRequest request) {
        clientInfo.update(
                request.clientName(),
                request.clientId(),
                request.clientSecret(),
                request.registeredRedirectUri(),
                request.accessTokenValiditySeconds(),
                request.scopes()
        );
    }
}
