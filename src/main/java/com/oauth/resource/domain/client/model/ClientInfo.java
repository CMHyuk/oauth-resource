package com.oauth.resource.domain.client.model;

import com.oauth.resource.global.domain.BaseEntity;
import com.oauth.resource.global.util.References;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Document(indexName = References.ELASTIC_INDEX_PREFIX_OAUTH_CLIENT + "*", createIndex = false)
@Setting(settingPath = "lower_case_normalizer_setting.json")
@SuppressWarnings("squid:S1948")
public class ClientInfo extends BaseEntity {

    private static final Integer VALIDITY_SECONDS = 300;

    private String tenantId;
    private String clientName;
    private String clientId;
    private String clientSecret;
    private Integer accessTokenValiditySeconds;

    @ElementCollection
    private Set<String> registeredRedirectUris = new HashSet<>();

    @ElementCollection
    private Set<String> scopes = new HashSet<>();

    public ClientInfo(String tenantId, String clientName, String clientId, String clientSecret, Set<String> registeredRedirectUris, Set<String> scopes) {
        this.tenantId = tenantId;
        this.clientName = clientName;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.registeredRedirectUris = registeredRedirectUris;
        this.scopes = scopes;
        this.accessTokenValiditySeconds = VALIDITY_SECONDS;
    }

    public void update(String clientName, String clientSecret, Set<String> registeredRedirectUris, Integer accessTokenValiditySeconds, Set<String> scopes) {
        this.clientName = clientName;
        this.clientSecret = clientSecret;
        this.registeredRedirectUris = registeredRedirectUris;
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        this.scopes = scopes;
    }
}
