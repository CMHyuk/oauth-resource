package com.oauth.resource.domain.tenant.service;

import com.oauth.resource.domain.tenant.dto.MasterTenantInfoSaveRequest;
import com.oauth.resource.domain.tenant.dto.MasterTenantSearchRequest;
import com.oauth.resource.domain.tenant.exception.TenantErrorCode;
import com.oauth.resource.domain.tenant.model.TenantInfo;
import com.oauth.resource.domain.tenant.repository.TenantInfoRepository;
import com.oauth.resource.global.exception.BusinessException;
import com.oauth.resource.global.util.KeyPairProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MasterTenantInfoService {

    private final TenantInfoRepository tenantInfoRepository;

    public TenantInfo save(MasterTenantInfoSaveRequest request) {
        String tenantId = UUID.randomUUID().toString();
        KeyPair keyPair = KeyPairProvider.generateKeyPair();
        TenantInfo tenantInfo = TenantInfo.createMasterTenant(request.tenantName(), keyPair.getPrivate().getEncoded(), keyPair.getPublic().getEncoded());
        return tenantInfoRepository.save(tenantId, tenantInfo);
    }

    public TenantInfo search(MasterTenantSearchRequest request) {
        return tenantInfoRepository.find(request)
                .orElseThrow(() -> BusinessException.from(TenantErrorCode.NOT_FOUND));
    }
}
