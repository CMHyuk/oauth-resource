package com.oauth.resource.domain.client.controller;

import com.oauth.resource.domain.client.dto.ClientInfoResponse;
import com.oauth.resource.domain.client.model.ClientInfo;
import com.oauth.resource.domain.client.service.MasterClientInfoQueryService;
import com.oauth.resource.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/master")
@RequiredArgsConstructor
public class MasterClientInfoQueryController {

    private final MasterClientInfoQueryService masterClientInfoQueryService;

    @GetMapping("/{tenantId}/client/v1/{clientId}")
    public ResponseEntity<ApiResponse> find(@PathVariable String tenantId, @PathVariable String clientId) {
        ClientInfo clientInfo = masterClientInfoQueryService.find(tenantId, clientId);
        ClientInfoResponse response = ClientInfoResponse.from(clientInfo);
        return ResponseEntity.ok().body(ApiResponse.success(response));
    }
}
