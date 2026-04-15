package com.merklys.api.auth.jwt;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String ROLES_CLAIM = "roles";

    private final JwtProperties jwtProperties;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.jwtProperties.getExpiration());

        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(username)
                .issuer(this.jwtProperties.getIssuer())
                .issuedAt(now)
                .expiration(expiryDate)
                .claim(ROLES_CLAIM, roles)
                .signWith(this.getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            this.parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT expirado: {}", e.getMessage());
        } catch (IncorrectClaimException e) {
            log.warn("JWT con claim inválido (issuer): {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("JWT no soportado: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("JWT malformado: {}", e.getMessage());
        } catch (SecurityException e) {
            log.warn("Firma JWT inválida: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT vacío o nulo: {}", e.getMessage());
        } catch (JwtException e) {
            log.warn("JWT inválido: {}", e.getMessage());
        }

        return false;
    }

    public String getUsernameFromToken(String token) {
        return this.parseClaims(token).getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        String roles = this.parseClaims(token).get(ROLES_CLAIM, String.class);

        if (roles == null || roles.isBlank()) {
            return Collections.emptyList();
        }

        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(this.getSigningKey())
                .requireIssuer(this.jwtProperties.getIssuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtProperties.getSecret());

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
