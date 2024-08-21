package com.oauth.resource.domain.tenant.dto;

import com.oauth.resource.domain.tenant.model.TenantInfo;

public record TenantInfoResponse(String tenantId, String tenantName) {

    public static TenantInfoResponse from(TenantInfo tenantInfo) {
        return new TenantInfoResponse(tenantInfo.getId(), tenantInfo.getTenantName());
    }
}
