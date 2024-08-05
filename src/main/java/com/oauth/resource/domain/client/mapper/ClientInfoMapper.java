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

    public ClientInfo createMasterClientInfo(String tenantId, MasterClientInfoSaveRequest request) {
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
                clientInfo.getTenantId(),
                request.clientName(),
                request.clientId(),
                request.clientSecret(),
                request.redirectUris(),
                request.accessTokenValiditySeconds(),
                request.scopes()
        );
    }
}
