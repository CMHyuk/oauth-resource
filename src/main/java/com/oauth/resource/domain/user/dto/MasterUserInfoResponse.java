package com.oauth.resource.domain.user.dto;

import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.model.UserRole;

import java.util.Set;

public record MasterUserInfoResponse(
        String username,
        String userId,
        String email,
        String password,
        Set<UserRole> userRole
) {

    public static MasterUserInfoResponse from(UserInfo userInfo) {
        return new MasterUserInfoResponse(
                userInfo.getUsername(),
                userInfo.getUserId(),
                userInfo.getEmail(),
                userInfo.getPassword(),
                userInfo.getRole()
        );
    }
}
