package com.oauth.resource.domain.tenant.controller;

import com.oauth.resource.domain.auth.LoginUser;
import com.oauth.resource.domain.tenant.dto.TenantInfoRequest;
import com.oauth.resource.domain.tenant.dto.TenantInfoResponse;
import com.oauth.resource.domain.tenant.model.TenantInfo;
import com.oauth.resource.domain.tenant.service.TenantInfoService;
import com.oauth.resource.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TenantInfoController {

    private final TenantInfoService tenantInfoService;

    @PostMapping("/v1/create")
    public ResponseEntity<ApiResponse> save(LoginUser loginUser, @RequestBody TenantInfoRequest request) {
        TenantInfo tenantInfo = tenantInfoService.save(loginUser, request);
        TenantInfoResponse response = new TenantInfoResponse(tenantInfo.getTenantName());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
