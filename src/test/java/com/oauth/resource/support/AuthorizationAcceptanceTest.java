package com.oauth.resource.support;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorizationAcceptanceTest extends SpringElasticSearchTestContainer {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://" + AUTHORIZATION_CONTAINER.getHost();
        RestAssured.port = AUTHORIZATION_CONTAINER.getFirstMappedPort();
    }
}
