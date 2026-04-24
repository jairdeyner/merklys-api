package com.merklys.api.auth.dto.response;

import java.util.Set;

public record AuthResponse(String tokenType, String accessToken, UserSummaryResponse user) {

    public record UserSummaryResponse(Long id, String username, String email, Set<String> roles) {

    }

    public static AuthResponse of(String accessToken, UserSummaryResponse user) {
        return new AuthResponse("Bearer", accessToken, user);
    }

}
