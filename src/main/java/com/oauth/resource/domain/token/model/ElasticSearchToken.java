package com.oauth.resource.domain.token.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oauth.resource.global.util.References;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Document(indexName = References.ELASTIC_INDEX_PREFIX_OAUTH_CLIENT_DETAILS + "*", createIndex = false)
@Setting(settingPath = "lower_case_normalizer_setting.json")
@Mapping(mappingPath = "access_token_mapping.json")
public class ElasticSearchToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String authorizationId;

    @Field(type = FieldType.Date,  format = {}, pattern = References.TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = References.TIME_FORMAT)
    private LocalDateTime expiresAt;

    private String username;
    private String accessToken;
    private String refreshToken;
    private String ipAddress;
}
