package com.oauth.resource.domain.user.service;

import com.oauth.resource.domain.user.dto.UserInfoSaveRequest;
import com.oauth.resource.domain.user.dto.MasterUserInfoUpdateRequest;
import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.domain.user.mapper.UserInfoMapper;
import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.domain.user.repository.UserInfoRepository;
import com.oauth.resource.domain.user.repository.UserInfoBaseRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterUserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;

    public UserInfo save(String tenantId, UserInfoSaveRequest request) {
        UserInfo masterUserInfo = userInfoMapper.createMasterUserInfo(tenantId, request);
        return userInfoRepository.save(tenantId, masterUserInfo);
    }

    public UserInfo find(String tenantId, String clientId) {
        return userInfoRepository.find(tenantId, clientId)
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND));
    }

    public void update(String tenantId, String userId, MasterUserInfoUpdateRequest request) {
        UserInfo masterUserInfo = getUserInfo(tenantId, userId);
        userInfoMapper.update(masterUserInfo, request);
        userInfoRepository.save(tenantId, masterUserInfo);
    }

    public void delete(String tenantId, String userId) {
        UserInfo masterUserInfo = getUserInfo(tenantId, userId);
        userInfoRepository.delete(tenantId, masterUserInfo);
    }

    private UserInfo getUserInfo(String tenantId, String userId) {
        return userInfoRepository.find(tenantId, userId)
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND));
    }
}
