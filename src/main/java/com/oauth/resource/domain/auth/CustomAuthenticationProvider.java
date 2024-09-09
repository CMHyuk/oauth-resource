package com.oauth.resource.domain.auth;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.oauth.resource.domain.tenant.service.TenantInfoService;
import com.oauth.resource.domain.token.exception.TokenErrorCode;
import com.oauth.resource.domain.token.repository.ElasticSearchTokenRepository;
import com.oauth.resource.global.exception.BusinessException;
import com.oauth.resource.global.exception.InternalServerErrorCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final ElasticSearchTokenRepository elasticSearchTokenRepository;
    private final TenantInfoService tenantInfoService;

    private final Log logger = LogFactory.getLog(this.getClass());
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter = new JwtAuthenticationConverter();

    public CustomAuthenticationProvider(ElasticSearchTokenRepository elasticSearchTokenRepository, TenantInfoService tenantInfoService) {
        this.elasticSearchTokenRepository = elasticSearchTokenRepository;
        this.tenantInfoService = tenantInfoService;
    }

    public Authentication authenticate(Authentication authentication) {
        BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
        Jwt jwt = this.getJwt(bearer);
        AbstractAuthenticationToken token = this.jwtAuthenticationConverter.convert(jwt);
        if (token.getDetails() == null) {
            token.setDetails(bearer.getDetails());
        }
        this.logger.debug("Authenticated token");
        return token;
    }

    private Jwt getJwt(BearerTokenAuthenticationToken bearer) {
        try {
            byte[] publicKeyBytes = getPublicKeyBytes(bearer);
            RSAPublicKey rsaPublicKey = loadPublicKey(publicKeyBytes);
            NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
            return decoder.decode(bearer.getToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getPublicKeyBytes(BearerTokenAuthenticationToken bearer) throws ParseException {
        String token = bearer.getToken();
        elasticSearchTokenRepository.findByAccessToken(token)
                .orElseThrow(() -> BusinessException.from(TokenErrorCode.NOT_FOUND));
        JWTClaimsSet jwtClaimsSet = JWTParser.parse(token).getJWTClaimsSet();
        String clientId = jwtClaimsSet.getAudience().get(0);
        return tenantInfoService.getKey(clientId).pubKey();
    }

    private RSAPublicKey loadPublicKey(byte[] publicKeyBytes) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw BusinessException.from(new InternalServerErrorCode(e.getMessage()));
        }
    }

    public boolean supports(Class<?> authentication) {
        return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
