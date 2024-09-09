package com.oauth.resource.resource.domain.auth;

import com.oauth.resource.domain.user.exception.UserErrorCode;
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

@TestClassesOrder(10)
public class RequiredRoleTest extends ResourceAcceptanceTest {

    @Nested
    @DisplayName("권한 검증이 필요한 요청에서")
    class RoleTest {

        @Test
        void 마스터_유저를_검증한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getMasterBearerToken())
                    .get("/resource/master")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 마스터_유저가_아닌_경우를_검증한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getAdminBearerToken())
                    .get("/resource/master")
                    .then().log().all()
                    .extract();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                    () -> assertThat(response.jsonPath().getString("code")).isEqualTo("3001"),
                    () -> assertThat(response.jsonPath().getString("errorMessage")).isEqualTo(UserErrorCode.FORBIDDEN.getMessage())
            );
        }

        @Test
        void 어드민_유저를_검증한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getAdminBearerToken())
                    .get("/resource/admin")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 어드민_유저가_아닌_경우를_검증한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getUserBearerAccessToken())
                    .get("/resource/admin")
                    .then().log().all()
                    .extract();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                    () -> assertThat(response.jsonPath().getString("code")).isEqualTo("3001"),
                    () -> assertThat(response.jsonPath().getString("errorMessage")).isEqualTo(UserErrorCode.FORBIDDEN.getMessage())
            );
        }

        @Test
        void 일반_유저는_권한_검증을_하지_않는다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getUserBearerAccessToken())
                    .get("/resource/ok")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }
}
