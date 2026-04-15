package com.merklys.api.auth.dto.response;

public record AuthResponse(String tokenType, String accessToken) {

    public static AuthResponse of(String accessToken) {
        return new AuthResponse("Bearer", accessToken);
    }

}
