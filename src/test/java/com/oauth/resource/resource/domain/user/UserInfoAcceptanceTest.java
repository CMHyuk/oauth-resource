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

@TestClassesOrder(7)
public class UserInfoAcceptanceTest extends ResourceAcceptanceTest {

    @Nested
    @DisplayName("유저를 생성할 때")
    class CreateUserInfoTest {

        @Test
        void 어드민_유저를_생성한다() {
            // given
            UserInfoSaveRequest request = new UserInfoSaveRequest(
                    "admin",
                    "admin@softcamp.co.kr",
                    "admin@email.com",
                    "password"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getAccessToken())
                    .body(request)
                    .post("/resource/api/admin/{tenant}/user/v1/create", tenantId)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 일반_유저를_생성한다() {
            // given
            UserInfoSaveRequest request = new UserInfoSaveRequest(
                    "user",
                    "user@softcamp.co.kr",
                    "user@email.com",
                    "password"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getAccessToken())
                    .body(request)
                    .post("/resource/api/tenant/v1/create")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }
}
