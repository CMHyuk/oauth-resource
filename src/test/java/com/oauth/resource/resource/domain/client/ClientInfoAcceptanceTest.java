package com.oauth.resource.resource.domain.client;

import com.oauth.resource.domain.client.dto.ClientInfoSaveRequest;
import com.oauth.resource.support.ResourceAcceptanceTest;
import com.oauth.resource.support.TestClassesOrder;
import com.oauth.resource.support.TokenContext;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestClassesOrder(6)
public class ClientInfoAcceptanceTest extends ResourceAcceptanceTest {

    @Test
    void 클라이언트를_생성한다() {
        // given
        String clientId = "clientId";
        String clientName = "clientName";
        String clientSecret = "clientSecret";
        Set<String> redirectUris = Set.of("http://locahost:8081");
        Set<String> scopes = Set.of("read");
        ClientInfoSaveRequest request = new ClientInfoSaveRequest(clientId, clientName, clientSecret, redirectUris, scopes);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, TokenContext.getAccessToken())
                .body(request)
                .post("/resource/api/{tenantId}/client/v1/create", tenantId)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.body().jsonPath().getString("response.clientId")).isEqualTo(clientId),
                () -> assertThat(response.body().jsonPath().getString("response.clientSecret")).isNotNull(),
                () -> assertThat(response.body().jsonPath().getString("response.redirectUris")).isEqualTo(redirectUris.toString()),
                () -> assertThat(response.body().jsonPath().getString("response.scopes")).isEqualTo(scopes.toString())
        );
    }
}
