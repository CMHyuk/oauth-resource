package com.oauth.resource.domain.client.service;

import com.oauth.resource.domain.client.exception.ClientErrorCode;
import com.oauth.resource.domain.client.model.ClientInfo;
import com.oauth.resource.domain.client.repository.ClientInfoQueryRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterClientInfoQueryService {

    private final ClientInfoQueryRepository clientInfoQueryRepository;

    public ClientInfo find(String tenantId, String clientId) {
        return clientInfoQueryRepository.find(tenantId, clientId)
                .orElseThrow(() -> BusinessException.from(ClientErrorCode.NOT_FOUND));
    }
}
