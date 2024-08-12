package com.oauth.resource.domain.user.service;

import com.oauth.resource.domain.auth.LoginUser;
import com.oauth.resource.domain.user.dto.UserInfoSaveRequest;
import com.oauth.resource.domain.user.mapper.UserInfoMapper;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.repository.UserInfoRepository;
import com.oauth.resource.domain.user.validator.UserInfoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoValidator userInfoValidator;
    private final UserInfoMapper userInfoMapper;

    public UserInfo saveAdminUser(LoginUser loginUser, String tenantId, UserInfoSaveRequest request) {
        userInfoValidator.validateMaster(loginUser.userId());
        UserInfo masterUserInfo = userInfoMapper.createAdminUserInfo(tenantId, request);
        return userInfoRepository.save(tenantId, masterUserInfo);
    }

    public UserInfo saveRegularUser(LoginUser loginUser, String tenantId, UserInfoSaveRequest request) {
        userInfoValidator.validateAdminOrMaster(loginUser.userId());
        UserInfo masterUserInfo = userInfoMapper.createUserInfo(tenantId, request);
        return userInfoRepository.save(tenantId, masterUserInfo);
    }
}
