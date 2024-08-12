package com.oauth.resource.domain.user.mapper;

import com.oauth.resource.domain.user.dto.UserInfoSaveRequest;
import com.oauth.resource.domain.user.dto.MasterUserInfoUpdateRequest;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserInfoMapper {

    private final PasswordEncoder passwordEncoder;

    public UserInfo createMasterUserInfo(String tenantId, UserInfoSaveRequest request) {
        return new UserInfo(
                tenantId,
                request.username(),
                request.userId(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Collections.singleton(UserRole.MASTER)
        );
    }

    public UserInfo createAdminUserInfo(String tenantId, UserInfoSaveRequest request) {
        return new UserInfo(
                tenantId,
                request.username(),
                request.userId(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Collections.singleton(UserRole.ADMIN)
        );
    }

    public UserInfo createUserInfo(String tenantId, UserInfoSaveRequest request) {
        return new UserInfo(
                tenantId,
                request.username(),
                request.userId(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Collections.singleton(UserRole.USER)
        );
    }

    public void update(UserInfo userInfo, MasterUserInfoUpdateRequest request) {
        userInfo.update(
                request.username(),
                request.userId(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
    }
}
