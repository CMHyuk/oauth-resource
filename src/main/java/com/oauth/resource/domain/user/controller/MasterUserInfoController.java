package com.oauth.resource.domain.user.controller;

import com.oauth.resource.domain.user.dto.UserInfoResponse;
import com.oauth.resource.domain.user.dto.UserInfoSaveRequest;
import com.oauth.resource.domain.user.dto.MasterUserInfoUpdateRequest;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.service.MasterUserInfoService;
import com.oauth.resource.global.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/master")
@RequiredArgsConstructor
public class MasterUserInfoController {

    private final MasterUserInfoService userInfoService;

    @PostMapping("/{tenantId}/user/v1/create")
    public ResponseEntity<ApiResponse> save(@PathVariable String tenantId, @Valid @RequestBody UserInfoSaveRequest request) {
        UserInfo userInfo = userInfoService.save(tenantId, request);
        UserInfoResponse response = UserInfoResponse.from(userInfo);
        return ResponseEntity.ok().body(ApiResponse.success(response));
    }

    @PostMapping("/{tenantId}/user/v1/{userId}/update")
    public ResponseEntity<ApiResponse> update(@PathVariable String tenantId, @PathVariable String userId, @Valid @RequestBody MasterUserInfoUpdateRequest request) {
        userInfoService.update(tenantId, userId, request);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

    @PostMapping("/{tenantId}/user/v1/{userId}/delete")
    public ResponseEntity<ApiResponse> delete(@PathVariable String tenantId, @PathVariable String userId) {
        userInfoService.delete(tenantId, userId);
        return ResponseEntity.ok().body(ApiResponse.success());
    }
}
