package com.oauth.resource.domain.client.dto;

import java.util.List;

public record MasterClientInfoSaveRequest(String clientName, List<String> scopes) {
}
