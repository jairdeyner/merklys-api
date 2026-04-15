package com.merklys.api.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Validated
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    @NotBlank(message = "JWT secret no puede estar vacio")
    private final String secret;

    @Positive(message = "JWT expiration debe ser mayor a 0")
    private final long expiration;

    @NotBlank(message = "JWT issuer no puede estar vacio")
    private final String issuer;

    @NotBlank(message = "JWT token prefix no puede estar vacio")
    private final String tokenPrefix;

    @NotBlank(message = "JWT header name no puede estar vacio")
    private final String headerName;

    public JwtProperties(String secret, long expiration, String issuer, String tokenPrefix, String headerName) {
        this.secret = secret;
        this.expiration = expiration;
        this.issuer = issuer;
        this.tokenPrefix = tokenPrefix;
        this.headerName = headerName;
    }

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public String getHeaderName() {
        return headerName;
    }

}
