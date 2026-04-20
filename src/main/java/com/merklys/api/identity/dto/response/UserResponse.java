package com.merklys.api.identity.dto.response;

import java.time.Instant;
import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        boolean isActive,
        boolean accountNonLocked,
        Set<RoleResponse> roles,
        Instant createdAt) {

}
