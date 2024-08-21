package com.oauth.resource.domain.client.repository;

import com.oauth.resource.domain.client.model.ClientInfo;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientInfoRepository {

    private static final String TENANT_ID_KEYWORD = "tenantId.keyword";
    private static final String CLIENT_ID_KEYWORD = "clientId.keyword";

    private final ClientInfoBaseRepository clientInfoBaseRepository;

    public ClientInfo save(String tenantId, ClientInfo clientInfo) {
        return clientInfoBaseRepository.save(tenantId, clientInfo);
    }

    public Optional<ClientInfo> find(String tenantId, String clientId) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(TENANT_ID_KEYWORD, tenantId))
                .filter(QueryBuilders.termQuery(CLIENT_ID_KEYWORD, clientId));
        return Optional.ofNullable(clientInfoBaseRepository.find(tenantId, query));
    }

    public Optional<ClientInfo> findByClientId(String clientId) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(CLIENT_ID_KEYWORD, clientId));
        ClientInfo clientInfo = clientInfoBaseRepository.find(null, query);
        return Optional.ofNullable(clientInfo);
    }

    public void delete(String tenantId, ClientInfo clientInfo) {
        clientInfoBaseRepository.delete(tenantId, clientInfo);
    }
}
