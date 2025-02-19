package com.oauth.resource.domain.tenant.service;

import com.oauth.resource.domain.client.model.ClientInfo;
import com.oauth.resource.domain.client.repository.ClientInfoRepository;
import com.oauth.resource.domain.tenant.dto.KeyResponse;
import com.oauth.resource.domain.tenant.dto.TenantInfoRequest;
import com.oauth.resource.domain.tenant.exception.TenantErrorCode;
import com.oauth.resource.domain.tenant.model.TenantInfo;
import com.oauth.resource.domain.tenant.repository.TenantInfoRepository;
import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.global.exception.BusinessException;
import com.oauth.resource.global.util.KeyPairProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantInfoService {

    private final ClientInfoRepository clientInfoRepository;
    private final TenantInfoRepository tenantInfoRepository;

    public TenantInfo save(TenantInfoRequest request) {
        String tenantId = UUID.randomUUID().toString();
        KeyPair keyPair = KeyPairProvider.generateKeyPair();
        TenantInfo tenantInfo = TenantInfo.createTenant(request.tenantName(), keyPair.getPrivate().getEncoded(), keyPair.getPublic().getEncoded());
        return tenantInfoRepository.save(tenantId, tenantInfo);
    }

    public KeyResponse getKey(String clientId) {
        ClientInfo clientInfo = clientInfoRepository.findByClientId(clientId)
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND));
        TenantInfo tenantInfo = tenantInfoRepository.findByTenantId(clientInfo.getTenantId())
                .orElseThrow(() -> BusinessException.from(TenantErrorCode.NOT_FOUND));
        return new KeyResponse(tenantInfo.getTenantRSAPublicKey(), tenantInfo.getTenantRSAPrivateKey());
    }
}
