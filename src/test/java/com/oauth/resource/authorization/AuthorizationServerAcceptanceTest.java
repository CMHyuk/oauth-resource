package com.oauth.resource.authorization;

import com.oauth.resource.domain.authorization.exception.OAuth2AuthorizationErrorCode;
import com.oauth.resource.domain.authorization.model.CustomOAuth2Authorization;
import com.oauth.resource.domain.authorization.repository.CustomOAuth2AuthorizationRepository;
import com.oauth.resource.global.exception.BusinessException;
import com.oauth.resource.support.AuthorizationAcceptanceTest;
import com.oauth.resource.support.TestClassesOrder;
import com.oauth.resource.support.TokenContext;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static com.oauth.resource.support.DocumentFieldConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@TestClassesOrder(4)
public class AuthorizationServerAcceptanceTest extends AuthorizationAcceptanceTest {

    @Autowired
    private CustomOAuth2AuthorizationRepository customOAuth2AuthorizationRepository;

    private Cookies cookies;
    private String code;

    @Test
    void 인가_서버에서_토큰을_발행한다() throws URISyntaxException {
        loginTest();
        consentPageTest();
        getCodeTest();
        getTokenTest();
    }

    private void loginTest() {
        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("username", USER_ID)
                .queryParam("password", PASSWORD)
                .post("/authorization/login")
                .then().log().all()
                .extract();

        // then
        cookies = response.detailedCookies();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FOUND.value());
    }

    private void consentPageTest() {
        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("response_type", "code")
                .queryParam("client_id", CLIENT_ID)
                .queryParam("scope", SCOPE)
                .queryParam("redirect_uri", REDIRECT_URI)
                .cookies(cookies)
                .get("/authorization/oauth2/authorize")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void getCodeTest() throws URISyntaxException {
        // given
        CustomOAuth2Authorization customOAuth2Authorization = customOAuth2AuthorizationRepository.findByCode()
                .orElseThrow(() -> BusinessException.from(OAuth2AuthorizationErrorCode.NOT_FOUND));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                .formParam("client_id", CLIENT_ID)
                .formParam("state", customOAuth2Authorization.getState())
                .formParam("scope", SCOPE)
                .cookies(cookies)
                .post("/authorization/oauth2/authorize")
                .then().log().all()
                .extract();

        // then
        code = getCode(response.header("Location"));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FOUND.value());
    }

    private void getTokenTest() {
        // given
        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedCredentials = Base64.encodeBase64String(credentials.getBytes());

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Basic " + encodedCredentials)
                .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                .formParam("code", code)
                .formParam("grant_type", "authorization_code")
                .formParam("redirect_uri", REDIRECT_URI)
                .cookies(cookies)
                .post("/authorization/oauth2/token")
                .then().log().all()
                .extract();

        // then
        TokenContext.setAccessToken(response.body().jsonPath().getString("access_token"));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private String getCode(String locationHeader) throws URISyntaxException {
        URI uri = new URI(locationHeader);
        String query = uri.getQuery();
        return Arrays.stream(query.split("&"))
                .filter(param -> param.startsWith("code="))
                .map(param -> param.split("=")[1])
                .findFirst()
                .orElse(null);
    }
}
