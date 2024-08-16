package com.oauth.resource.domain.authorization.model;

import com.oauth.resource.global.util.References;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@Document(indexName = References.ELASTIC_INDEX_PREFIX_OAUTH_CODE + "*", createIndex = false)
@Setting(settingPath = "lower_case_normalizer_setting.json")
public class CustomOAuth2Authorization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String code;
    private String state;
    private String authorizationId;
    private String oAuth2Authorization;
}
