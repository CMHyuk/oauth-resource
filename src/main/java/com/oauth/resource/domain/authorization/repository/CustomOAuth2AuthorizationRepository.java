package com.oauth.resource.domain.authorization.repository;


import com.oauth.resource.domain.authorization.model.CustomOAuth2Authorization;
import com.oauth.resource.elasticsearch.base.CustomAwareRepository;

public interface CustomOAuth2AuthorizationRepository extends CustomAwareRepository<CustomOAuth2Authorization, String> {
}
