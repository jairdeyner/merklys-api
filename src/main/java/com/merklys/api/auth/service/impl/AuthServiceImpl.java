package com.merklys.api.auth.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.merklys.api.auth.dto.request.LoginRequest;
import com.merklys.api.auth.dto.response.AuthResponse;
import com.merklys.api.auth.dto.response.UserSummaryResponse;
import com.merklys.api.auth.jwt.JwtTokenProvider;
import com.merklys.api.auth.security.CustomUserDetails;
import com.merklys.api.auth.service.AuthService;
import com.merklys.api.common.util.SecurityUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityUtils securityUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
            SecurityUtils securityUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityUtils = securityUtils;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.identifier().toLowerCase().trim(), request.password()));

        String token = this.jwtTokenProvider.generateToken(authentication);

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        UserSummaryResponse userSummary = this.toUserSummary(user);

        return AuthResponse.of(token, userSummary);
    }

    @Override
    public UserSummaryResponse getAuthenticatedUser() {
        CustomUserDetails user = this.securityUtils.getCurrentUser();

        return this.toUserSummary(user);
    }

    private UserSummaryResponse toUserSummary(CustomUserDetails user) {
        Set<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring(5))
                .collect(Collectors.toSet());

        UserSummaryResponse userSummary = new UserSummaryResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles);

        return userSummary;
    }

}
