package com.oauth.resource.domain.user.repository;

import com.oauth.resource.domain.user.model.UserInfo;
import com.oauth.resource.elasticsearch.base.CustomAwareRepository;

public interface UserInfoRepository extends CustomAwareRepository<UserInfo, String> {
}
