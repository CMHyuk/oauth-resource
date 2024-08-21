package com.oauth.resource.domain.tenant.repository;

import com.oauth.resource.domain.tenant.dto.MasterTenantSearchRequest;
import com.oauth.resource.domain.tenant.model.TenantInfo;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TenantInfoRepository {

    private static final String ID_KEYWORD = "_id";
    private static final String TENANT_NAME_KEYWORD = "tenantName.keyword";

    private final TenantInfoBaseRepository tenantInfoBaseRepository;

    public TenantInfo save(String tenantId, TenantInfo tenantInfo) {
        return tenantInfoBaseRepository.save(tenantId, tenantInfo);
    }

    public Optional<TenantInfo> find(MasterTenantSearchRequest request) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(TENANT_NAME_KEYWORD, request.tenantName()));
        TenantInfo tenantInfo = tenantInfoBaseRepository.find(null, query);
        return Optional.ofNullable(tenantInfo);
    }

    public Optional<TenantInfo> findByTenantId(String tenantId) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(ID_KEYWORD, tenantId));
        TenantInfo tenantInfo = tenantInfoBaseRepository.find(null, query);
        return Optional.ofNullable(tenantInfo);
    }
}
