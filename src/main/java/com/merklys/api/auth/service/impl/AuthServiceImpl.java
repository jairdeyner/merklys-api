package com.merklys.api.auth.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.merklys.api.auth.dto.request.LoginRequest;
import com.merklys.api.auth.dto.response.AuthResponse;
import com.merklys.api.auth.jwt.JwtTokenProvider;
import com.merklys.api.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.identifier().toLowerCase().trim(), request.password()));

        String token = this.jwtTokenProvider.generateToken(authentication);

        return AuthResponse.of(token);
    }

}
