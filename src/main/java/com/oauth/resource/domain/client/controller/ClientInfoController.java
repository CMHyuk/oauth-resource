package com.oauth.resource.domain.client.controller;

import com.oauth.resource.domain.auth.LoginUser;
import com.oauth.resource.domain.client.dto.ClientInfoResponse;
import com.oauth.resource.domain.client.dto.ClientInfoSaveRequest;
import com.oauth.resource.domain.client.model.ClientInfo;
import com.oauth.resource.domain.client.service.ClientInfoService;
import com.oauth.resource.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientInfoController {

    private final ClientInfoService clientInfoService;

    @PostMapping("/{tenantId}/client/v1/create")
    public ResponseEntity<ApiResponse> save(LoginUser loginUser, @PathVariable String tenantId, @RequestBody ClientInfoSaveRequest request) {
        ClientInfo clientInfo = clientInfoService.save(loginUser, tenantId, request);
        return ResponseEntity.ok(ApiResponse.success(ClientInfoResponse.from(clientInfo)));
    }
}
