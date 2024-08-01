package com.oauth.resource.domain.user.service;

import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.repository.UserInfoQueryRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterUserInfoQueryService {

    private final UserInfoQueryRepository userInfoQueryRepository;

    public UserInfo find(String tenantId, String clientId) {
        return userInfoQueryRepository.find(tenantId, clientId)
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND));
    }
}
