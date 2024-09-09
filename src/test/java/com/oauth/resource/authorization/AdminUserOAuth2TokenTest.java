package com.oauth.resource.authorization;

import com.oauth.resource.domain.authorization.repository.CustomOAuth2AuthorizationBaseRepository;
import com.oauth.resource.support.AuthorizationAcceptanceTest;
import com.oauth.resource.support.TestClassesOrder;
import com.oauth.resource.support.TokenContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.oauth.resource.support.DocumentFieldConstants.*;

@TestClassesOrder(8)
public class AdminUserOAuth2TokenTest extends AuthorizationAcceptanceTest {

    @Autowired
    private CustomOAuth2AuthorizationBaseRepository customOAuth2AuthorizationBaseRepository;

    @Test
    void 어드민_유저가_토큰을_발행한다() {
        TokenProvider tokenProvider = new TokenProvider(customOAuth2AuthorizationBaseRepository);
        String accessToken = tokenProvider.issueToken(ADMIN_USER_ID, ADMIN_USER_PASSWORD);
        TokenContext.setAdminUserAccessToken(accessToken);
    }
}
