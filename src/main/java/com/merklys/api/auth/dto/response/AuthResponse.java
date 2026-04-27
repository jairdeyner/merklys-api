package com.merklys.api.auth.dto.response;

public record AuthResponse(String tokenType, String accessToken, UserSummaryResponse user) {

    public static AuthResponse of(String accessToken, UserSummaryResponse user) {
        return new AuthResponse("Bearer", accessToken, user);
    }

}
