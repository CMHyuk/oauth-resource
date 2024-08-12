package com.oauth.resource.domain.client.service;

import com.oauth.resource.domain.auth.LoginUser;
import com.oauth.resource.domain.client.dto.ClientInfoSaveRequest;
import com.oauth.resource.domain.client.mapper.ClientInfoMapper;
import com.oauth.resource.domain.client.model.ClientInfo;
import com.oauth.resource.domain.client.repository.ClientInfoRepository;
import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.domain.user.repository.UserInfoQueryRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientInfoService {

    private final UserInfoQueryRepository userInfoQueryRepository;
    private final ClientInfoRepository clientInfoRepository;
    private final ClientInfoMapper clientInfoMapper;

    public ClientInfo save(LoginUser loginUser, String tenantId, ClientInfoSaveRequest request) {
        userInfoQueryRepository.findByUserId(loginUser.userId())
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND))
                .validateMaster();
        ClientInfo clientInfo = clientInfoMapper.createClientInfo(tenantId, request);
        return clientInfoRepository.save(tenantId, clientInfo);
    }
}
