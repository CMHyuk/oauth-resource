package com.oauth.resource.domain.user.controller;

import com.oauth.resource.domain.auth.LoginUser;
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
    public ResponseEntity<ApiResponse> saveAdminUser(LoginUser loginUser, @PathVariable String tenantId, @RequestBody UserInfoSaveRequest request) {
        UserInfo userInfo = userInfoService.saveAdminUser(loginUser, tenantId, request);
        UserInfoResponse response = UserInfoResponse.from(userInfo);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{tenantId}/user/v1/create")
    public ResponseEntity<ApiResponse> saveRegularUser(LoginUser loginUser, @PathVariable String tenantId, @RequestBody UserInfoSaveRequest request) {
        UserInfo userInfo = userInfoService.saveRegularUser(loginUser, tenantId, request);
        UserInfoResponse response = UserInfoResponse.from(userInfo);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
