package com.oauth.resource.domain.user.repository;

import com.oauth.resource.domain.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserInfoRepository {

    private static final String TENANT_ID_KEYWORD = "tenantId.keyword";
    private static final String USER_ID_KEYWORD = "userId.keyword";

    private final UserInfoBaseRepository userInfoBaseRepository;

    public UserInfo save(String tenantId, UserInfo userInfo) {
        return userInfoBaseRepository.save(tenantId, userInfo);
    }

    public Optional<UserInfo> find(String tenantId, String userId) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(TENANT_ID_KEYWORD, tenantId))
                .filter(QueryBuilders.termQuery(USER_ID_KEYWORD, userId));
        return Optional.ofNullable(userInfoBaseRepository.find(tenantId, query));
    }

    public Optional<UserInfo> findByUserId(String userId) {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(USER_ID_KEYWORD, userId));
        return Optional.ofNullable(userInfoBaseRepository.find(null, query));
    }

    public void delete(String tenantId, UserInfo userInfo) {
        userInfoBaseRepository.delete(tenantId, userInfo);
    }
}
