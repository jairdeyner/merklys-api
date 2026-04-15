package com.merklys.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "El usuario o el email es requerido") String identifier,
        @NotBlank(message = "La contraseña es requerida") String password) {

}
