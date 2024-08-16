package com.oauth.resource.domain.token.repository;


import com.oauth.resource.domain.token.model.ElasticSearchToken;
import com.oauth.resource.elasticsearch.base.CustomAwareRepository;

public interface ElasticSearchTokenRepository extends CustomAwareRepository<ElasticSearchToken, String> {
}
