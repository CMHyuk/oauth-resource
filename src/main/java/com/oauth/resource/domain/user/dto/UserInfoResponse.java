package com.oauth.resource.domain.user.dto;

import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.model.UserRole;

import java.util.Set;

public record UserInfoResponse(
        String username,
        String userId,
        String email,
        String password,
        Set<UserRole> userRole
) {

    public static UserInfoResponse from(UserInfo userInfo) {
        return new UserInfoResponse(
                userInfo.getUsername(),
                userInfo.getUserId(),
                userInfo.getEmail(),
                userInfo.getPassword(),
                userInfo.getRole()
        );
    }
}
