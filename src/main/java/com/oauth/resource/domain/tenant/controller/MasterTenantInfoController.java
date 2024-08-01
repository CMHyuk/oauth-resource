package com.oauth.resource.domain.tenant.controller;

import com.oauth.resource.domain.tenant.dto.MasterTenantInfoResponse;
import com.oauth.resource.domain.tenant.dto.MasterTenantInfoSaveRequest;
import com.oauth.resource.domain.tenant.model.TenantInfo;
import com.oauth.resource.domain.tenant.service.MasterTenantInfoService;
import com.oauth.resource.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
public class MasterTenantInfoController {

    private final MasterTenantInfoService masterTenantInfoService;

    @PostMapping("/tenant/v1/create")
    public ResponseEntity<ApiResponse> save(@RequestBody MasterTenantInfoSaveRequest request) {
        TenantInfo tenantInfo = masterTenantInfoService.save(request);
        MasterTenantInfoResponse response = MasterTenantInfoResponse.from(tenantInfo);
        return ResponseEntity.ok().body(ApiResponse.success(response));
    }
}
