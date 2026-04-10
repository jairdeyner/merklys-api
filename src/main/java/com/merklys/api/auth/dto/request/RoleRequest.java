package com.merklys.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequest(
        @NotBlank(message = "El nombre es requerido") @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres") String name) {

}
