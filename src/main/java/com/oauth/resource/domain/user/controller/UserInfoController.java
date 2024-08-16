package com.oauth.resource.domain.user.controller;

import com.oauth.resource.domain.auth.RequiredAdmin;
import com.oauth.resource.domain.auth.RequiredMaster;
import com.oauth.resource.domain.user.dto.UserInfoResponse;
import com.oauth.resource.domain.user.dto.UserInfoSaveRequest;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.service.UserInfoService;
import com.oauth.resource.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @PostMapping("/admin/{tenantId}/user/v1/create")
    @RequiredMaster
    public ResponseEntity<ApiResponse> saveAdminUser(@PathVariable String tenantId, @RequestBody UserInfoSaveRequest request) {
        UserInfo userInfo = userInfoService.saveAdminUser(tenantId, request);
        UserInfoResponse response = UserInfoResponse.from(userInfo);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{tenantId}/user/v1/create")
    @RequiredAdmin
    public ResponseEntity<ApiResponse> saveRegularUser(@PathVariable String tenantId, @RequestBody UserInfoSaveRequest request) {
        UserInfo userInfo = userInfoService.saveRegularUser(tenantId, request);
        UserInfoResponse response = UserInfoResponse.from(userInfo);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
