package com.oauth.resource.resource.domain.client;

import com.oauth.resource.domain.client.dto.ClientInfoSaveRequest;
import com.oauth.resource.support.ResourceAcceptanceTest;
import com.oauth.resource.support.TestClassesOrder;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Set;

import static com.oauth.resource.support.DocumentFieldConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestClassesOrder(2)
public class MasterClientInfoAcceptanceTest extends ResourceAcceptanceTest {

    @Nested
    @DisplayName("마스터 클라이언트를")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class MasterClientTest {

        @Test
        @Order(1)
        void 저장한다() {
            // given
            ClientInfoSaveRequest request = new ClientInfoSaveRequest(
                    CLIENT_ID,
                    "oauth-client-name",
                    CLIENT_SECRET,
                    Set.of(REDIRECT_URI),
                    Set.of(SCOPE)
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/{clientId}/client/v1/create", tenantId)
                    .then().log().all()
                    .extract();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(response.body().jsonPath().get("response.clientId").toString()).isEqualTo("oauth-client-id"),
                    () -> assertThat(response.body().jsonPath().get("response.clientSecret").toString()).isNotNull(),
                    () -> assertThat(response.body().jsonPath().get("response.redirectUris").toString()).isEqualTo("[http://127.0.0.1:8081]"),
                    () -> assertThat(response.body().jsonPath().get("response.scopes").toString()).isEqualTo("[read]")
            );
        }

        @Test
        @Order(2)
        void 조회한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .get("/resource/api/master/{tenantId}/client/v1/{clientId}", tenantId, CLIENT_ID)
                    .then().log().all()
                    .extract();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(response.body().jsonPath().get("response.clientId").toString()).isEqualTo("oauth-client-id"),
                    () -> assertThat(response.body().jsonPath().get("response.clientSecret").toString()).isNotNull(),
                    () -> assertThat(response.body().jsonPath().get("response.redirectUris").toString()).isEqualTo("[http://127.0.0.1:8081]"),
                    () -> assertThat(response.body().jsonPath().get("response.scopes").toString()).isEqualTo("[read]")
            );
        }

        @Test
        void 조회할_때_존재하지_않으면_예외가_발생한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .get("/resource/api/master/{tenantId}/client/v1/{clientId}", "NOT EXISTED", "NOT EXISTED")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

//        @Test
//        @Order(3)
//        void 수정한다() {
//            // given
//            MasterClientInfoUpdateRequest request = new MasterClientInfoUpdateRequest(
//                    "updateName",
//                    "updateSecret",
//                    Set.of("http://127.0.0.1:8081"),
//                    Set.of("read", "delete"),
//                    200
//            );
//
//            // when
//            ExtractableResponse<Response> response = RestAssured.given().log().all()
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .body(request)
//                    .post("/resource/api/master/{tenantId}/client/v1/{clientId}/update", TENANT_ID, CLIENT_ID)
//                    .then().log().all()
//                    .extract();
//
//            // then
//            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//        }
//
//        @Test
//        @Order(4)
//        void 삭제한다() {
//            // when
//            ExtractableResponse<Response> response = RestAssured.given().log().all()
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .post("/resource/api/master/{tenantId}/client/v1/{clientId}/delete", TENANT_ID, CLIENT_ID)
//                    .then().log().all()
//                    .extract();
//
//            // then
//            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//        }
    }
}
