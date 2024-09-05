package com.oauth.resource.resource.domain.user;

import com.oauth.resource.domain.user.dto.UserInfoSaveRequest;
import com.oauth.resource.domain.user.dto.MasterUserInfoUpdateRequest;
import com.oauth.resource.support.ResourceAcceptanceTest;
import com.oauth.resource.support.TestClassesOrder;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.oauth.resource.support.DocumentFieldConstants.PASSWORD;
import static com.oauth.resource.support.DocumentFieldConstants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestClassesOrder(3)
public class MasterUserInfoAcceptanceTest extends ResourceAcceptanceTest {

    @Nested
    @DisplayName("마스터 사용자를")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class MasterUserInfoTest {

        @Test
        @Order(1)
        void 저장한다() {
            // given
            UserInfoSaveRequest request = new UserInfoSaveRequest(
                    "master-name",
                    USER_ID,
                    "master@example.com",
                    PASSWORD
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/{tenantId}/user/v1/create", tenantId)
                    .then().log().all()
                    .extract();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(response.body().jsonPath().getString("response.username")).isEqualTo("master-name"),
                    () -> assertThat(response.body().jsonPath().getString("response.userId")).isEqualTo("master@email.com"),
                    () -> assertThat(response.body().jsonPath().getString("response.email")).isEqualTo("master@example.com"),
                    () -> assertThat(response.body().jsonPath().getString("response.password")).isNotNull(),
                    () -> assertThat(response.body().jsonPath().getString("response.userRole")).isEqualTo("[MASTER]")
            );
        }

        @Test
        @Order(2)
        void 저장할_때_유저_아이디가_이메일_형식에_맞지_않으면_예외가_발생한다() {
            // given
            UserInfoSaveRequest request = new UserInfoSaveRequest(
                    "master-name",
                    "master",
                    "master@example.com",
                    PASSWORD
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/{tenantId}/user/v1/create", tenantId)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @Order(3)
        void 저장할_때_이메일이__형식에_맞지_않으면_예외가_발생한다() {
            // given
            UserInfoSaveRequest request = new UserInfoSaveRequest(
                    "master-name",
                    USER_ID,
                    "master",
                    PASSWORD
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/{tenantId}/user/v1/create", tenantId)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @Order(4)
        void 조회한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .get("/resource/api/master/{tenantId}/user/v1/{userId}", tenantId, USER_ID)
                    .then().log().all()
                    .extract();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(response.body().jsonPath().getString("response.username")).isEqualTo("master-name"),
                    () -> assertThat(response.body().jsonPath().getString("response.userId")).isEqualTo("master@email.com"),
                    () -> assertThat(response.body().jsonPath().getString("response.email")).isEqualTo("master@example.com"),
                    () -> assertThat(response.body().jsonPath().getString("response.password")).isNotNull(),
                    () -> assertThat(response.body().jsonPath().getString("response.userRole")).isEqualTo("[MASTER]")
            );
        }

        @Test
        void 조회할_때_존재하지_않으면_예외가_발생한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .get("/resource/api/master/{tenantId}/user/v1/{userId}", "NOT EXISTED", "NOT EXISTED")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

//        @Test
//        @Order(5)
//        void 수정한다() {
//            // given
//            MasterUserInfoUpdateRequest request = new MasterUserInfoUpdateRequest(
//                    "updateName",
//                    "update@example.com",
//                    "update1234!"
//            );
//
//            // when
//            ExtractableResponse<Response> response = RestAssured.given().log().all()
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .body(request)
//                    .post("/resource/api/master/{tenantId}/user/v1/{userId}/update", TENANT_ID, USER_ID)
//                    .then().log().all()
//                    .extract();
//
//            // then
//            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//        }

        @Test
        @Order(6)
        void 수정할_때_이메일이_형식에_맞지_않으면_예외가_발생한다() {
            // given
            MasterUserInfoUpdateRequest request = new MasterUserInfoUpdateRequest(
                    "updateName",
                    "update",
                    "update1234!"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/{tenantId}/user/v1/{userId}/update", tenantId, USER_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

//        @Test
//        @Order(7)
//        void 삭제한다() {
//            // when
//            ExtractableResponse<Response> response = RestAssured.given().log().all()
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .post("/resource/api/master/{tenantId}/user/v1/{userId}/delete", TENANT_ID, USER_ID)
//                    .then().log().all()
//                    .extract();
//
//            // then
//            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//        }
    }
}
