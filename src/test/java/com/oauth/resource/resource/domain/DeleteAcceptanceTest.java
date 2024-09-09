package com.oauth.resource.resource.domain;

import com.oauth.resource.domain.token.dto.AccessTokenDeleteRequest;
import com.oauth.resource.support.ResourceAcceptanceTest;
import com.oauth.resource.support.TestClassesOrder;
import com.oauth.resource.support.TokenContext;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


import static com.oauth.resource.support.DocumentFieldConstants.CLIENT_ID;
import static com.oauth.resource.support.DocumentFieldConstants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

@TestClassesOrder(9)
public class DeleteAcceptanceTest extends ResourceAcceptanceTest {

    @Nested
    @DisplayName("마스터 클라이언트를")
    class MasterClientUpdateAndDeleteTest {

        @Test
        void 삭제한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .post("/resource/api/master/{tenantId}/client/v1/{clientId}/delete", tenantId, CLIENT_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }

    @Nested
    @DisplayName("마스터 유저를")
    class MasterUserUpdateAndDeleteTest {

        @Test
        void 삭제한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .post("/resource/api/master/{tenantId}/user/v1/{userId}/delete", tenantId, USER_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }

    @Nested
    @DisplayName("발급된 토큰을")
    class AccessTokenDeleteTest {

        @Test
        void 삭제한다() {
            // given
            AccessTokenDeleteRequest request = new AccessTokenDeleteRequest(TokenContext.getAccessToken());

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getBearerToken())
                    .body(request)
                    .delete("/resource/api/v1/access-token")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }
}
