package com.merklys.api.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Component;

import com.merklys.api.auth.security.CustomUserDetails;

@Component
public class SecurityUtils {

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    public CustomUserDetails getCurrentUser() {
        Authentication authentication = this.securityContextHolderStrategy.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No hay un usuario autenticado en el contexto de seguridad");
        }

        return (CustomUserDetails) authentication.getPrincipal();
    }

    public Long getCurrentUserId() {
        return this.getCurrentUser().getId();
    }

    public String getCurrentUsername() {
        return this.getCurrentUser().getUsername();
    }

}
