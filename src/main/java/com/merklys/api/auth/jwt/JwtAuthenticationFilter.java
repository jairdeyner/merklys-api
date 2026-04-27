package com.merklys.api.auth.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.merklys.api.auth.security.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final CustomUserDetailsService customUserDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, JwtProperties jwtProperties,
            CustomUserDetailsService customUserDetailsService,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
        this.customUserDetailsService = customUserDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = extractToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!this.jwtTokenProvider.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (this.securityContextHolderStrategy.getContext().getAuthentication() == null) {
            this.authenticateRequest(token, request, response);
        }

        if (response.isCommitted()) {
            return;
        }

        filterChain.doFilter(request, response);

    }

    private void authenticateRequest(String token, HttpServletRequest request, HttpServletResponse response) {
        try {
            Long userId = this.jwtTokenProvider.getUserIdFromToken(token);

            UserDetails userDetails = this.customUserDetailsService.loadUserById(userId);

            if (!userDetails.isEnabled()) {
                log.warn("Usuario inactivo intentó autenticarse: {}", userDetails.getUsername());
                handlerExceptionResolver.resolveException(request, response, null,
                        new DisabledException("Cuenta inactiva"));
                return;
            }

            if (!userDetails.isAccountNonLocked()) {
                log.warn("Usuario bloqueado intentó autenticarse: {}", userDetails.getUsername());
                handlerExceptionResolver.resolveException(request, response, null,
                        new LockedException("Cuenta bloqueada"));
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            this.securityContextHolderStrategy.setContext(context);

        } catch (Exception e) {
            log.warn("No se pudo autenticar el request: {}", e.getMessage());
        }

    }

    private String extractToken(HttpServletRequest request) {
        String headerValue = request.getHeader(this.jwtProperties.getHeaderName());

        if (!StringUtils.hasText(headerValue)) {
            return null;
        }

        String prefix = this.jwtProperties.getTokenPrefix() + " ";

        if (!headerValue.startsWith(prefix)) {
            return null;
        }

        return headerValue.substring(prefix.length());
    }

}
