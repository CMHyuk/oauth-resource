package com.oauth.resource.domain.tenant.controller;

import com.oauth.resource.domain.tenant.dto.MasterTenantInfoResponse;
import com.oauth.resource.domain.tenant.dto.MasterTenantSearchRequest;
import com.oauth.resource.domain.tenant.model.TenantInfo;
import com.oauth.resource.domain.tenant.service.MasterTenantInfoQueryService;
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
public class MasterTenantInfoQueryController {

    private final MasterTenantInfoQueryService masterTenantInfoQueryService;

    @PostMapping("/tenant/v1/search")
    public ResponseEntity<ApiResponse> search(@RequestBody MasterTenantSearchRequest request) {
        TenantInfo tenantInfo = masterTenantInfoQueryService.search(request);
        MasterTenantInfoResponse response = MasterTenantInfoResponse.from(tenantInfo);
        return ResponseEntity.ok().body(ApiResponse.success(response));
    }
}
