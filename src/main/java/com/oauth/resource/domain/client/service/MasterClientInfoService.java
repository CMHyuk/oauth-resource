package com.oauth.resource.domain.client.service;

import com.oauth.resource.domain.client.dto.ClientInfoSaveRequest;
import com.oauth.resource.domain.client.dto.MasterClientInfoUpdateRequest;
import com.oauth.resource.domain.client.exception.ClientErrorCode;
import com.oauth.resource.domain.client.mapper.ClientInfoMapper;
import com.oauth.resource.domain.client.model.ClientInfo;
import com.oauth.resource.domain.client.repository.ClientInfoQueryRepository;
import com.oauth.resource.domain.client.repository.ClientInfoRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterClientInfoService {

    private final ClientInfoQueryRepository clientInfoQueryRepository;
    private final ClientInfoRepository clientInfoRepository;
    private final ClientInfoMapper clientInfoMapper;

    public ClientInfo save(String tenantId, ClientInfoSaveRequest request) {
        ClientInfo clientInfo = clientInfoMapper.createClientInfo(tenantId, request);
        return clientInfoRepository.save(tenantId, clientInfo);
    }

    public void update(String tenantId, String clientId, MasterClientInfoUpdateRequest request) {
        ClientInfo clientInfo = getClientInfo(tenantId, clientId);
        clientInfoMapper.update(clientInfo, request);
        clientInfoRepository.save(tenantId, clientInfo);
    }


    public void delete(String tenantId, String clientId) {
        ClientInfo clientInfo = getClientInfo(tenantId, clientId);
        clientInfoRepository.delete(tenantId, clientInfo);
    }

    private ClientInfo getClientInfo(String tenantId, String clientId) {
        return clientInfoQueryRepository.find(tenantId, clientId)
                .orElseThrow(() -> BusinessException.from(ClientErrorCode.NOT_FOUND));
    }
}
