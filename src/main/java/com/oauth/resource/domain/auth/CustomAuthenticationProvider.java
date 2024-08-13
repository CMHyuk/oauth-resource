package com.oauth.resource.domain.auth;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.oauth.resource.domain.tenant.dto.KeyResponse;
import com.oauth.resource.domain.tenant.service.TenantInfoService;
import com.oauth.resource.global.exception.BusinessException;
import com.oauth.resource.global.exception.InternalServerErrorCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.util.Assert;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final TenantInfoService tenantInfoService;

    private final Log logger = LogFactory.getLog(this.getClass());
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter = new JwtAuthenticationConverter();

    public CustomAuthenticationProvider(TenantInfoService tenantInfoService) {
        this.tenantInfoService = tenantInfoService;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken)authentication;
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
        } catch (BadJwtException var3) {
            BadJwtException failed = var3;
            this.logger.debug("Failed to authenticate since the JWT was invalid");
            throw new InvalidBearerTokenException(failed.getMessage(), failed);
        } catch (JwtException var4) {
            JwtException failed = var4;
            throw new AuthenticationServiceException(failed.getMessage(), failed);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getPublicKeyBytes(BearerTokenAuthenticationToken bearer) throws ParseException {
        String token = bearer.getToken();
        JWT parse = JWTParser.parse(token);
        JWTClaimsSet jwtClaimsSet = parse.getJWTClaimsSet();
        String clientId = jwtClaimsSet.getAudience().get(0);
        KeyResponse key = tenantInfoService.getKey(clientId);
        return key.pubKey();
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

    public void setJwtAuthenticationConverter(Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter) {
        Assert.notNull(jwtAuthenticationConverter, "jwtAuthenticationConverter cannot be null");
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }
}
