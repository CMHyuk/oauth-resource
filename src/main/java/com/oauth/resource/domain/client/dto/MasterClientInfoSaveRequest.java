package com.oauth.resource.domain.client.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record MasterClientInfoSaveRequest(
        @NotBlank String clientId,
        @NotBlank String clientName,
        @NotBlank String clientSecret,
        @NotBlank Set<String> redirectUris,
        @NotBlank Set<String> scopes
) {
}
