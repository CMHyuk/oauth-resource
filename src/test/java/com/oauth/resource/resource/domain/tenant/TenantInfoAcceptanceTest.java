package com.oauth.resource.resource.domain.tenant;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestClassesOrder(5)
public class TenantInfoAcceptanceTest extends ResourceAcceptanceTest {

    @Test
    void 테넌트를_생성한다() {
        // given
        String tenantName = "sk";
        TenantInfoRequest request = new TenantInfoRequest(tenantName);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, TokenContext.getAccessToken())
                .body(request)
                .post("/resource/api/tenant/v1/create")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () ->  assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () ->  assertThat(response.body().jsonPath().getString("response.tenantName")).isEqualTo(tenantName)
        );
    }
}
