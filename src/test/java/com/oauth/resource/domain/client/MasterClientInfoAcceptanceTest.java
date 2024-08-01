package com.oauth.resource.domain.client;

import com.oauth.resource.domain.client.dto.MasterClientInfoSaveRequest;
import com.oauth.resource.domain.client.dto.MasterClientInfoUpdateRequest;
import com.oauth.resource.support.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MasterClientInfoAcceptanceTest extends AcceptanceTest {

    private static final String TENANT_ID = UUID.randomUUID().toString();
    private static final String CLIENT_ID = "oauth-client-id";
    private static final String UPDATED_CLIENT_ID = "update-oauth-id";

    @Nested
    @DisplayName("마스터 클라이언트를")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class MasterClientTest {

        @Test
        @Order(1)
        void 저장한다() {
            // given
            MasterClientInfoSaveRequest request = new MasterClientInfoSaveRequest("test-client", List.of("read", "write"));

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/master/{clientId}/client/v1/create", TENANT_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        @Order(2)
        void 조회한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .get("/master/{tenantId}/client/v1/{clientId}", TENANT_ID, CLIENT_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 조회할_때_존재하지_않으면_예외가_발생한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .get("/master/{tenantId}/client/v1/{clientId}", "NOT EXISTED", "NOT EXISTED")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @Order(3)
        void 수정한다() {
            // given
            MasterClientInfoUpdateRequest request = new MasterClientInfoUpdateRequest(
                    "updateName",
                    UPDATED_CLIENT_ID,
                    "updateSecret",
                    "updateRedirectUri",
                    200,
                    List.of("read", "delete")
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/master/{tenantId}/client/v1/{clientId}/update", TENANT_ID, CLIENT_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        @Order(4)
        void 삭제한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .post("/master/{tenantId}/client/v1/{clientId}/delete", TENANT_ID, UPDATED_CLIENT_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }
}
