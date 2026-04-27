package com.merklys.api.auth.service;

import com.merklys.api.auth.dto.request.LoginRequest;
import com.merklys.api.auth.dto.response.AuthResponse;
import com.merklys.api.auth.dto.response.UserSummaryResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    UserSummaryResponse getAuthenticatedUser();

}
