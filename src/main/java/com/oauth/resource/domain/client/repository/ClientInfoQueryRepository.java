package com.oauth.resource.domain.client.repository;

import com.oauth.resource.domain.client.model.ClientInfo;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientInfoQueryRepository {

    private static final String TENANT_ID_KEYWORD = "tenantId.keyword";
    private static final String CLIENT_ID_KEYWORD = "clientId.keyword";

    private final ClientInfoRepository clientInfoRepository;

    public Optional<ClientInfo> find(String tenantId, String clientId) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(TENANT_ID_KEYWORD, tenantId))
                .filter(QueryBuilders.termQuery(CLIENT_ID_KEYWORD, clientId));
        return Optional.ofNullable(clientInfoRepository.find(tenantId, query));
    }

    public Optional<ClientInfo> findByClientId(String clientId) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(CLIENT_ID_KEYWORD, clientId));
        ClientInfo clientInfo = clientInfoRepository.find(null, query);
        return Optional.ofNullable(clientInfo);
    }
}
