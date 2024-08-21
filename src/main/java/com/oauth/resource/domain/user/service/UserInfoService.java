package com.oauth.resource.domain.user.service;

import com.oauth.resource.domain.user.dto.UserInfoSaveRequest;
import com.oauth.resource.domain.user.mapper.UserInfoMapper;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.repository.UserInfoBaseRepository;
import com.oauth.resource.domain.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;

    public UserInfo saveAdminUser(String tenantId, UserInfoSaveRequest request) {
        UserInfo masterUserInfo = userInfoMapper.createAdminUserInfo(tenantId, request);
        return userInfoRepository.save(tenantId, masterUserInfo);
    }

    public UserInfo saveRegularUser(String tenantId, UserInfoSaveRequest request) {
        UserInfo masterUserInfo = userInfoMapper.createUserInfo(tenantId, request);
        return userInfoRepository.save(tenantId, masterUserInfo);
    }
}
