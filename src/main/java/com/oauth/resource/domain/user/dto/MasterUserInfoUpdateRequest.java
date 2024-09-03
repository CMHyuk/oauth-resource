package com.oauth.resource.domain.user.dto;

import jakarta.validation.constraints.Email;

public record MasterUserInfoUpdateRequest(
        String username,
        @Email String email,
        String password
) {
}
