package com.oauth.resource.domain.token.controller;

import com.oauth.resource.domain.auth.RequiredMaster;
import com.oauth.resource.domain.token.dto.AccessTokenDeleteRequest;
import com.oauth.resource.domain.token.service.ElasticSearchTokenService;
import com.oauth.resource.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TokenController {

    private final ElasticSearchTokenService elasticSearchTokenService;

    @DeleteMapping("/v1/access-token")
    @RequiredMaster
    public ResponseEntity<ApiResponse> deleteAccessToken(@RequestBody AccessTokenDeleteRequest request) {
        elasticSearchTokenService.delete(request.accessToken());
        return ResponseEntity.ok(ApiResponse.success());
    }
}
