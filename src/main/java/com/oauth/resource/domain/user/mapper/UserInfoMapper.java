package com.oauth.resource.domain.user.mapper;

import com.oauth.resource.domain.user.config.PasswordEncoder;
import com.oauth.resource.domain.user.dto.MasterUserInfoSaveRequest;
import com.oauth.resource.domain.user.dto.MasterUserInfoUpdateRequest;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserInfoMapper {

    private final PasswordEncoder passwordEncoder;

    public UserInfo createMasterUserInfo(String tenantId, MasterUserInfoSaveRequest request) {
        return new UserInfo(
                tenantId,
                request.username(),
                request.userId(),
                request.email(),
                passwordEncoder.encrypt(request.password()),
                Collections.singleton(UserRole.MASTER)
        );
    }

    public void update(UserInfo userInfo, MasterUserInfoUpdateRequest request) {
        userInfo.update(
                request.username(),
                request.userId(),
                request.email(),
                passwordEncoder.encrypt(request.password())
        );
    }
}
