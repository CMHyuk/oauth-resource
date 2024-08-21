package com.oauth.resource.domain.client.repository;

import com.oauth.resource.domain.client.model.ClientInfo;
import com.oauth.resource.elasticsearch.base.CustomAwareRepository;

public interface ClientInfoBaseRepository extends CustomAwareRepository<ClientInfo, String> {
}
