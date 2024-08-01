package com.oauth.resource.domain.user.repository;

import com.oauth.resource.domain.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserInfoQueryRepository {

    private static final String TENANT_ID_KEYWORD = "tenantId.keyword";
    private static final String USER_ID_KEYWORD = "userId.keyword";

    private final UserInfoRepository userInfoRepository;

    public Optional<UserInfo> find(String tenantId, String userId) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(TENANT_ID_KEYWORD, tenantId))
                .filter(QueryBuilders.termQuery(USER_ID_KEYWORD, userId));
        return Optional.ofNullable(userInfoRepository.find(tenantId, query));
    }
}
