package com.oauth.resource.domain.user.validator;

import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.domain.user.repository.UserInfoRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoValidator {

    private final UserInfoRepository userInfoRepository;

    public void validateMaster(String userId) {
        userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND))
                .validateMaster();
    }

    public void validateAdminOrMaster(String userId) {
        userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND))
                .validateAdminOrMaster();
    }
}
