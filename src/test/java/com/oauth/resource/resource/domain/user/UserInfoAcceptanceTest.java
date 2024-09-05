package com.oauth.resource.resource.domain.user;

import com.oauth.resource.domain.user.dto.UserInfoSaveRequest;
import com.oauth.resource.support.ResourceAcceptanceTest;
import com.oauth.resource.support.TestClassesOrder;
import com.oauth.resource.support.TokenContext;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestClassesOrder(7)
public class UserInfoAcceptanceTest extends ResourceAcceptanceTest {

    @Nested
    @DisplayName("유저를 생성할 때")
    class CreateUserInfoTest {

        @Test
        void 어드민_유저를_생성한다() {
            // given
            String username = "admin";
            String userId = "admin@softcamp.co.kr";
            String email = "admin@email.com";
            String password = "password";
            UserInfoSaveRequest request = new UserInfoSaveRequest(username, userId, email, password);

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getAccessToken())
                    .body(request)
                    .post("/resource/api/admin/{tenant}/user/v1/create", tenantId)
                    .then().log().all()
                    .extract();

            // then
            assertAll(
                    () ->  assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () ->  assertThat(response.body().jsonPath().getString("response.username")).isEqualTo(username),
                    () ->  assertThat(response.body().jsonPath().getString("response.userId")).isEqualTo(userId),
                    () ->  assertThat(response.body().jsonPath().getString("response.email")).isEqualTo(email),
                    () ->  assertThat(response.body().jsonPath().getString("response.password")).isNotNull(),
                    () -> assertThat(response.body().jsonPath().getString("response.userRole")).isEqualTo("[ADMIN]")
            );
        }

        @Test
        void 일반_유저를_생성한다() {
            // given
            String username = "user";
            String userId = "user@softcamp.co.kr";
            String email = "user@email.com";
            String password = "password";
            UserInfoSaveRequest request = new UserInfoSaveRequest(username, userId, email, password);

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getAccessToken())
                    .body(request)
                    .post("/resource/api/{tenantId}/user/v1/create", tenantId)
                    .then().log().all()
                    .extract();

            // then
            assertAll(
                    () ->  assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () ->  assertThat(response.body().jsonPath().getString("response.username")).isEqualTo(username),
                    () ->  assertThat(response.body().jsonPath().getString("response.userId")).isEqualTo(userId),
                    () ->  assertThat(response.body().jsonPath().getString("response.email")).isEqualTo(email),
                    () ->  assertThat(response.body().jsonPath().getString("response.password")).isNotNull(),
                    () -> assertThat(response.body().jsonPath().getString("response.userRole")).isEqualTo("[USER]")
            );
        }
    }
}
