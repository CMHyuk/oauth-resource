package com.oauth.resource.domain.client.mapper;

import com.oauth.resource.domain.client.dto.ClientInfoSaveRequest;
import com.oauth.resource.domain.client.dto.MasterClientInfoUpdateRequest;
import com.oauth.resource.domain.client.model.ClientInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientInfoMapper {

    private final PasswordEncoder passwordEncoder;

    public ClientInfo createClientInfo(String tenantId, ClientInfoSaveRequest request) {
        return new ClientInfo(
                tenantId,
                request.clientName(),
                request.clientId(),
                passwordEncoder.encode(request.clientSecret()),
                request.redirectUris(),
                request.scopes()
        );
    }

    public void update(ClientInfo clientInfo, MasterClientInfoUpdateRequest request) {
        clientInfo.update(
                request.clientName(),
                request.clientSecret(),
                request.redirectUris(),
                request.accessTokenValiditySeconds(),
                request.scopes()
        );
    }
}
