package com.oauth.resource.domain.tenant.service;

import com.oauth.resource.domain.tenant.dto.MasterTenantInfoSaveRequest;
import com.oauth.resource.domain.tenant.model.TenantInfo;
import com.oauth.resource.domain.tenant.repository.TenantInfoRepository;
import com.oauth.resource.global.exception.BusinessException;
import com.oauth.resource.global.exception.InternalServerErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MasterTenantInfoService {

    private static final String ALGORITHM = "RSA";

    private final TenantInfoRepository tenantInfoRepository;

    public TenantInfo save(MasterTenantInfoSaveRequest request) {
        String tenantId = UUID.randomUUID().toString();
        KeyPair keyPair = generateKeyPair();
        TenantInfo tenantInfo = TenantInfo.createMasterTenant(request.tenantName(), keyPair.getPrivate().getEncoded(), keyPair.getPublic().getEncoded());
        return tenantInfoRepository.save(tenantId, tenantInfo);
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw BusinessException.from(new InternalServerErrorCode(e.getMessage()));
        }
    }
}
