package com.oauth.resource.domain.token.service;

import com.oauth.resource.domain.token.exception.TokenErrorCode;
import com.oauth.resource.domain.token.model.ElasticSearchToken;
import com.oauth.resource.domain.token.repository.ElasticSearchTokenQueryRepository;
import com.oauth.resource.domain.token.repository.ElasticSearchTokenRepository;
import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.repository.UserInfoQueryRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElasticSearchTokenService {

    private final ElasticSearchTokenQueryRepository elasticSearchTokenQueryRepository;
    private final ElasticSearchTokenRepository elasticSearchTokenRepository;
    private final UserInfoQueryRepository userInfoQueryRepository;

    public void delete(String accessToken) {
        ElasticSearchToken elasticSearchToken = elasticSearchTokenQueryRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> BusinessException.from(TokenErrorCode.NOT_FOUND));
        UserInfo userInfo = userInfoQueryRepository.findByUserId(elasticSearchToken.getUsername())
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND));
        elasticSearchTokenRepository.delete(userInfo.getTenantId(), elasticSearchToken);
    }
}
