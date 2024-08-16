package com.oauth.resource.domain.token.repository;

import com.oauth.resource.domain.token.model.ElasticSearchToken;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ElasticSearchTokenQueryRepository {

    private final ElasticSearchTokenRepository elasticSearchTokenRepository;

    private static final String ACCESS_TOKEN_KEYWORD = "accessToken.keyword";

    public Optional<ElasticSearchToken> findByAccessToken(String accessToken) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(ACCESS_TOKEN_KEYWORD, accessToken));
        ElasticSearchToken elasticSearchToken = elasticSearchTokenRepository.find(null, query);
        return Optional.ofNullable(elasticSearchToken);
    }
}
