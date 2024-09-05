package com.oauth.resource.resource.domain.client;

import com.oauth.resource.domain.client.dto.ClientInfoSaveRequest;
import com.oauth.resource.domain.tenant.dto.TenantInfoRequest;
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

@TestClassesOrder(6)
public class ClientInfoAcceptanceTest extends ResourceAcceptanceTest {

    @Test
    void 클라이언트를_생성한다() {
        // given
        ClientInfoSaveRequest request = new ClientInfoSaveRequest(
                "clientId",
                "clientName",
                "ClientSecret",
                Set.of("http://locahost:8081"),
                Set.of("read")
        );

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, TokenContext.getAccessToken())
                .body(request)
                .post("/resource/api/{tenantId}/client/v1/create", tenantId)
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
