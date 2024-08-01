package com.oauth.resource.domain.tenant.repository;

import com.oauth.resource.domain.tenant.model.TenantInfo;
import com.oauth.resource.elasticsearch.base.CustomAwareRepository;

public interface TenantInfoRepository extends CustomAwareRepository<TenantInfo, String> {
}
