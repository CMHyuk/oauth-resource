package com.oauth.resource.domain.token.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oauth.resource.global.domain.BaseEntity;
import com.oauth.resource.global.util.References;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Document(indexName = References.ELASTIC_INDEX_PREFIX_OAUTH_CLIENT_DETAILS + "*", createIndex = false)
@Setting(settingPath = "lower_case_normalizer_setting.json")
public class ElasticSearchToken extends BaseEntity {

    private String authorizationId;

    @Field(type = FieldType.Date,  format = {}, pattern = References.TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = References.TIME_FORMAT)
    private LocalDateTime expiresAt;

    private String username;

    @Field(type = FieldType.Text)
    private String accessToken;
    private String refreshToken;
    private String ipAddress;
}
