package com.merklys.api.auth.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final UserDetailsService userdetailsservice;

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, JwtProperties jwtProperties,
            UserDetailsService userdetailsservice) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
        this.userdetailsservice = userdetailsservice;
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
            this.authenticateRequest(token, request);
        }

        filterChain.doFilter(request, response);

    }

    private void authenticateRequest(String token, HttpServletRequest request) {
        try {
            String username = this.jwtTokenProvider.getUsernameFromToken(token);

            UserDetails userDetails = this.userdetailsservice.loadUserByUsername(username);

            if (!userDetails.isEnabled() || !userDetails.isAccountNonLocked()) {
                log.warn("Usuario inactivo o bloqueado intentó autenticarse: {}", username);
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
