package com.oauth.resource.domain.client.controller;

import com.oauth.resource.domain.client.dto.MasterClientInfoResponse;
import com.oauth.resource.domain.client.dto.MasterClientInfoSaveRequest;
import com.oauth.resource.domain.client.dto.MasterClientInfoUpdateRequest;
import com.oauth.resource.domain.client.model.ClientInfo;
import com.oauth.resource.domain.client.service.MasterClientInfoService;
import com.oauth.resource.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
public class MasterClientInfoController {

    private final MasterClientInfoService masterClientInfoService;

    @PostMapping("/{tenantId}/client/v1/create")
    public ResponseEntity<ApiResponse> save(@PathVariable String tenantId, @RequestBody MasterClientInfoSaveRequest request) {
        ClientInfo clientInfo = masterClientInfoService.save(tenantId, request);
        MasterClientInfoResponse response = MasterClientInfoResponse.from(clientInfo);
        return ResponseEntity.ok().body(ApiResponse.success(response));
    }

    @PostMapping("/{tenantId}/client/v1/{clientId}/update")
    public ResponseEntity<ApiResponse> update(@PathVariable String tenantId, @PathVariable String clientId, @RequestBody MasterClientInfoUpdateRequest request) {
        masterClientInfoService.update(tenantId, clientId, request);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

    @PostMapping("/{tenantId}/client/v1/{clientId}/delete")
    public ResponseEntity<ApiResponse> delete(@PathVariable String tenantId, @PathVariable String clientId) {
        masterClientInfoService.delete(tenantId, clientId);
        return ResponseEntity.ok().body(ApiResponse.success());
    }
}
