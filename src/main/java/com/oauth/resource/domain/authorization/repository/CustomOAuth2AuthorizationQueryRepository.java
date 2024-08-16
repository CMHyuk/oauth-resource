package com.oauth.resource.domain.authorization.repository;

import com.oauth.resource.domain.authorization.model.CustomOAuth2Authorization;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomOAuth2AuthorizationQueryRepository {

    private static final String AUTHORIZATION_KEYWORD = "authorizationId.keyword";

    private final CustomOAuth2AuthorizationRepository oauthAuthorizationRepository;

    public Optional<CustomOAuth2Authorization> findByAuthorizationId(String authorizationId) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(AUTHORIZATION_KEYWORD, authorizationId));
        CustomOAuth2Authorization customOAuth2Authorization = oauthAuthorizationRepository.find(null, query);
        return Optional.ofNullable(customOAuth2Authorization);
    }
}
