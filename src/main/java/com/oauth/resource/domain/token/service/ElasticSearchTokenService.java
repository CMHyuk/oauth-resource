package com.oauth.resource.domain.token.service;

import com.oauth.resource.domain.authorization.repository.CustomOAuth2AuthorizationRepository;
import com.oauth.resource.domain.token.exception.TokenErrorCode;
import com.oauth.resource.domain.token.model.ElasticSearchToken;
import com.oauth.resource.domain.token.repository.ElasticSearchTokenRepository;
import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.repository.UserInfoRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElasticSearchTokenService {

    private final CustomOAuth2AuthorizationRepository customOAuth2AuthorizationRepository;
    private final ElasticSearchTokenRepository elasticSearchTokenRepository;
    private final UserInfoRepository userInfoRepository;

    public void delete(String accessToken) {
        ElasticSearchToken elasticSearchToken = elasticSearchTokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> BusinessException.from(TokenErrorCode.NOT_FOUND));
        UserInfo userInfo = userInfoRepository.findByUserId(elasticSearchToken.getUsername())
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND));
        String tenantId = userInfo.getTenantId();
        elasticSearchTokenRepository.delete(tenantId, elasticSearchToken);
        deleteOAuth2Authorization(elasticSearchToken, tenantId);
    }

    private void deleteOAuth2Authorization(ElasticSearchToken elasticSearchToken, String tenantId) {
        customOAuth2AuthorizationRepository.findByAuthorizationId(elasticSearchToken.getAuthorizationId())
                        .ifPresent(authorization -> customOAuth2AuthorizationRepository.delete(tenantId, authorization));
    }
}
