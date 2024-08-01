package com.oauth.resource.domain.tenant.dto;

import com.oauth.resource.domain.tenant.model.TenantInfo;

public record MasterTenantInfoResponse(String id, String name, byte[] privateKey, byte[] publicKey) {

    public static MasterTenantInfoResponse from(TenantInfo tenantInfo) {
        return new MasterTenantInfoResponse(tenantInfo.getId(), tenantInfo.getTenantName(), tenantInfo.getTenantRSAPrivateKey(), tenantInfo.getTenantRSAPublicKey());
    }
}
