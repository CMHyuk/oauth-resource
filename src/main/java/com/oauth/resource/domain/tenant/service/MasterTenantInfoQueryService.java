package com.oauth.resource.domain.tenant.service;

import com.oauth.resource.domain.tenant.dto.MasterTenantSearchRequest;
import com.oauth.resource.domain.tenant.exception.TenantErrorCode;
import com.oauth.resource.domain.tenant.model.TenantInfo;
import com.oauth.resource.domain.tenant.repository.TenantInfoQueryRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterTenantInfoQueryService {

    private final TenantInfoQueryRepository tenantInfoQueryRepository;

    public TenantInfo search(MasterTenantSearchRequest request) {
        return tenantInfoQueryRepository.find(request)
                .orElseThrow(() -> BusinessException.from(TenantErrorCode.NOT_FOUND));
    }
}
