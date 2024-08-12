package com.oauth.resource.domain.tenant.service;

import com.oauth.resource.domain.auth.LoginUser;
import com.oauth.resource.domain.tenant.dto.TenantInfoRequest;
import com.oauth.resource.domain.tenant.model.TenantInfo;
import com.oauth.resource.domain.tenant.repository.TenantInfoRepository;
import com.oauth.resource.domain.user.validator.UserInfoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantInfoService {

    private final UserInfoValidator userInfoValidator;
    private final TenantInfoRepository tenantInfoRepository;

    public TenantInfo save(LoginUser loginUser, TenantInfoRequest request) {
        userInfoValidator.validateMaster(loginUser.userId());
        String tenantId = UUID.randomUUID().toString();
        TenantInfo tenantInfo = TenantInfo.createTenant(request.tenantName());
        return tenantInfoRepository.save(tenantId, tenantInfo);
    }
}
