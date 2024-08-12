package com.oauth.resource.domain.tenant.service;

import com.oauth.resource.domain.auth.LoginUser;
import com.oauth.resource.domain.tenant.dto.TenantInfoRequest;
import com.oauth.resource.domain.tenant.model.TenantInfo;
import com.oauth.resource.domain.tenant.repository.TenantInfoRepository;
import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.domain.user.repository.UserInfoQueryRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantInfoService {

    private final TenantInfoRepository tenantInfoRepository;
    private final UserInfoQueryRepository userInfoQueryRepository;

    public TenantInfo save(LoginUser loginUser, TenantInfoRequest request) {
        userInfoQueryRepository.findByUserId(loginUser.userId())
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND))
                .validateMaster();
        String tenantId = UUID.randomUUID().toString();
        TenantInfo tenantInfo = TenantInfo.createTenant(request.tenantName());
        return tenantInfoRepository.save(tenantId, tenantInfo);
    }
}
