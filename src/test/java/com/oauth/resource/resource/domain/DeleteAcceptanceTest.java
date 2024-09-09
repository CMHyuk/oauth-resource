package com.oauth.resource.resource.domain;

import com.oauth.resource.domain.client.repository.ClientInfoRepository;
import com.oauth.resource.domain.token.dto.AccessTokenDeleteRequest;
import com.oauth.resource.domain.token.repository.ElasticSearchTokenRepository;
import com.oauth.resource.domain.user.repository.UserInfoRepository;
import com.oauth.resource.support.ResourceAcceptanceTest;
import com.oauth.resource.support.TestClassesOrder;
import com.oauth.resource.support.TokenContext;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


import static com.oauth.resource.support.DocumentFieldConstants.CLIENT_ID;
import static com.oauth.resource.support.DocumentFieldConstants.MASTER_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestClassesOrder(11)
public class DeleteAcceptanceTest extends ResourceAcceptanceTest {

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ElasticSearchTokenRepository elasticSearchTokenRepository;

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
            assertAll(
                    () -> assertThat(clientInfoRepository.findByClientId(CLIENT_ID)).isEmpty(),
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
            );
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
                    .post("/resource/api/master/{tenantId}/user/v1/{userId}/delete", tenantId, MASTER_USER_ID)
                    .then().log().all()
                    .extract();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(userInfoRepository.findByUserId(MASTER_USER_ID)).isEmpty()
            );
        }
    }

    @Nested
    @DisplayName("발급된 토큰을 삭제할 때")
    class AccessTokenDeleteTest {

        @Test
        void 정상적으로_삭제한다() {
            // given
            AccessTokenDeleteRequest request = new AccessTokenDeleteRequest(TokenContext.getMasterUserAccessToken());

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getMasterBearerToken())
                    .body(request)
                    .delete("/resource/api/v1/access-token")
                    .then().log().all()
                    .extract();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(elasticSearchTokenRepository.findByAccessToken(request.accessToken())).isEmpty()
            );
        }

        @Test
        void 존재하지_않은_토큰은_예외가_발생한다() {
            // given
            AccessTokenDeleteRequest request = new AccessTokenDeleteRequest("token");

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, TokenContext.getMasterBearerToken())
                    .body(request)
                    .delete("/resource/api/v1/access-token")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }
    }
}
