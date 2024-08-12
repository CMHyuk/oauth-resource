package com.oauth.resource.domain.user.validator;

import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.domain.user.repository.UserInfoQueryRepository;
import com.oauth.resource.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoValidator {

    private final UserInfoQueryRepository userInfoQueryRepository;

    public void validateMaster(String userId) {
        userInfoQueryRepository.findByUserId(userId)
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND))
                .validateMaster();
    }

    public void validateAdminOrMaster(String userId) {
        userInfoQueryRepository.findByUserId(userId)
                .orElseThrow(() -> BusinessException.from(UserErrorCode.NOT_FOUND))
                .validateAdminOrMaster();
    }
}
