package com.oauth.resource.domain.user.dto;

import jakarta.validation.constraints.Email;

public record MasterUserInfoUpdateRequest(
        String username,
        @Email String userId,
        @Email String email,
        String password
) {
}
