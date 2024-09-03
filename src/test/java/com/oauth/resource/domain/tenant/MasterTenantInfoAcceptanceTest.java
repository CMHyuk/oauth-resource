package com.oauth.resource.domain.tenant;

import com.oauth.resource.domain.tenant.dto.MasterTenantInfoSaveRequest;
import com.oauth.resource.domain.tenant.dto.MasterTenantSearchRequest;
import com.oauth.resource.support.AcceptanceTest;
import com.oauth.resource.support.TestClassesOrder;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@TestClassesOrder(1)
public class MasterTenantInfoAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("마스터 테넌트를")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class MasterTenantSaveTest {

        @Test
        @Order(1)
        void 저장한다() {
            // given
            MasterTenantInfoSaveRequest request = new MasterTenantInfoSaveRequest("master");

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/tenant/v1/create")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        @Order(2)
        void 저장할_때_요청_이름이_MASTER가_아니면_예외가_발생한다() {
            // given
            MasterTenantInfoSaveRequest request = new MasterTenantInfoSaveRequest("not-master");

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/tenant/v1/create")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @Order(3)
        void 조회한다() {
            // given
            MasterTenantSearchRequest request = new MasterTenantSearchRequest("master");

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/tenant/v1/search")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 조회할_때_존재하지_않으면_예외가_발생한다() {
            // given
            MasterTenantSearchRequest request = new MasterTenantSearchRequest("NOT EXISTED");

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .post("/resource/api/master/tenant/v1/search")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }
    }
}
