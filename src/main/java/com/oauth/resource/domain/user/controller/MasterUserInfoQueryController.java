package com.oauth.resource.domain.user.controller;

import com.oauth.resource.domain.user.dto.MasterUserInfoResponse;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.service.MasterUserInfoQueryService;
import com.oauth.resource.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
public class MasterUserInfoQueryController {

    private final MasterUserInfoQueryService masterUserInfoQueryService;

    @GetMapping("/{tenantId}/user/v1/{userId}")
    public ResponseEntity<ApiResponse> find(@PathVariable String tenantId, @PathVariable String userId) {
        UserInfo userInfo = masterUserInfoQueryService.find(tenantId, userId);
        MasterUserInfoResponse response = MasterUserInfoResponse.from(userInfo);
        return ResponseEntity.ok().body(ApiResponse.success(response));
    }
}
