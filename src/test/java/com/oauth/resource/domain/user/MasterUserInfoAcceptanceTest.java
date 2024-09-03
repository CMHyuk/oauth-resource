package com.oauth.resource.domain.user;

import com.oauth.resource.domain.user.dto.UserInfoSaveRequest;
import com.oauth.resource.domain.user.dto.MasterUserInfoUpdateRequest;
import com.oauth.resource.support.AcceptanceTest;
import com.oauth.resource.support.TestClassesOrder;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@TestClassesOrder(3)
public class MasterUserInfoAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("마스터 사용자를")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class MasterUserInfoTest {

        private static final String TENANT_ID = UUID.randomUUID().toString();
        private static final String USER_ID = "master@email.com";

        @Test
        @Order(1)
        void 저장한다() {
            // given
            UserInfoSaveRequest request = new UserInfoSaveRequest(
                    "master-name",
                    USER_ID,
                    "master@example.com",
                    "password123!"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/{tenantId}/user/v1/create", TENANT_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        @Order(2)
        void 저장할_때_유저_아이디가_이메일_형식에_맞지_않으면_예외가_발생한다() {
            // given
            UserInfoSaveRequest request = new UserInfoSaveRequest(
                    "master-name",
                    "master",
                    "master@example.com",
                    "password123!"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/{tenantId}/user/v1/create", TENANT_ID)
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
                    "master@email.com",
                    "master",
                    "password123!"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/{tenantId}/user/v1/create", TENANT_ID)
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
                    .get("/resource/api/master/{tenantId}/user/v1/{userId}", TENANT_ID, USER_ID)
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
                    .get("/resource/api/master/{tenantId}/user/v1/{userId}", "NOT EXISTED", "NOT EXISTED")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @Order(5)
        void 수정한다() {
            // given
            MasterUserInfoUpdateRequest request = new MasterUserInfoUpdateRequest(
                    "updateName",
                    "update@example.com",
                    "update1234!"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/{tenantId}/user/v1/{userId}/update", TENANT_ID, USER_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

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
                    .post("/resource/api/master/{tenantId}/user/v1/{userId}/update", TENANT_ID, USER_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @Order(7)
        void 삭제한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .post("/resource/api/master/{tenantId}/user/v1/{userId}/delete", TENANT_ID, USER_ID)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }
}
