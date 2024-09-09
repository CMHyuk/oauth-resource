package com.oauth.resource.resource.domain.auth;

import com.oauth.resource.domain.auth.RequiredAdmin;
import com.oauth.resource.domain.auth.RequiredMaster;
import com.oauth.resource.global.exception.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequiredRoleTestController {

    @GetMapping("/master")
    @RequiredMaster
    public ResponseEntity<ApiResponse> master() {
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/admin")
    @RequiredAdmin
    public ResponseEntity<ApiResponse> admin() {
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/ok")
    public ResponseEntity<ApiResponse> ok() {
        return ResponseEntity.ok(ApiResponse.success());
    }
}
