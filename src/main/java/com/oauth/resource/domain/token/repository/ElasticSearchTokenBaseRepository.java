package com.oauth.resource.domain.token.repository;


import com.oauth.resource.domain.token.model.ElasticSearchToken;
import com.oauth.resource.elasticsearch.base.CustomAwareRepository;

public interface ElasticSearchTokenBaseRepository extends CustomAwareRepository<ElasticSearchToken, String> {
}
