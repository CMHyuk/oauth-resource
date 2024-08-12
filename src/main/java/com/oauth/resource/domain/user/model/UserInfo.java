package com.oauth.resource.domain.user.model;

import com.oauth.resource.domain.user.exception.UserErrorCode;
import com.oauth.resource.global.exception.BusinessException;
import com.oauth.resource.global.util.References;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Document(indexName = References.ELASTIC_INDEX_PREFIX_OAUTH_USER + "*", createIndex = false)
@SuppressWarnings("JpaAttributeTypeInspection")
public class UserInfo {

    @Id
    private String id;

    private String tenantId;
    private String username;
    private String userId;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Set<UserRole> role;

    public UserInfo(String tenantId, String username, String userId, String email, String password, Set<UserRole> role) {
        this.tenantId = tenantId;
        this.username = username;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void update(String username, String userId, String email, String password) {
        this.username = username;
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public void validateAdminOrMaster() {
        if (!isAdmin() && !isMaster()) {
            throw BusinessException.from(UserErrorCode.UNAUTHORIZED);
        }
    }

    private boolean isAdmin() {
        return this.getRole().contains(UserRole.ADMIN);
    }

    public void validateMaster() {
        if (!isMaster()) {
            throw BusinessException.from(UserErrorCode.UNAUTHORIZED);
        }
    }

    private boolean isMaster() {
        return this.getRole().contains(UserRole.MASTER);
    }
}
