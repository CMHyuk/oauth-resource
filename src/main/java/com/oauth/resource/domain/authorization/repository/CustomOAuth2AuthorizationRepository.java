package com.oauth.resource.domain.authorization.repository;

import com.oauth.resource.domain.authorization.model.CustomOAuth2Authorization;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomOAuth2AuthorizationRepository {

    private static final String AUTHORIZATION_KEYWORD = "authorizationId.keyword";
    private static final String CODE = "code.keyword";

    private final CustomOAuth2AuthorizationBaseRepository oauthAuthorizationRepository;

    public Optional<CustomOAuth2Authorization> findByAuthorizationId(String authorizationId) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(AUTHORIZATION_KEYWORD, authorizationId));
        CustomOAuth2Authorization customOAuth2Authorization = oauthAuthorizationRepository.find(null, query);
        return Optional.ofNullable(customOAuth2Authorization);
    }

    public Optional<CustomOAuth2Authorization> findByCode() {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery(CODE, "EMPTY_CODE"));
        CustomOAuth2Authorization customOAuth2Authorization = oauthAuthorizationRepository.find(null, query);
        return Optional.ofNullable(customOAuth2Authorization);
    }

    public void delete(String tenantId, CustomOAuth2Authorization customOAuth2Authorization) {
        oauthAuthorizationRepository.delete(tenantId, customOAuth2Authorization);
    }
}
