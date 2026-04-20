package com.merklys.api.identity.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio") @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres") String username,
        @NotBlank(message = "El email es obligatorio") @Email(message = "El email no tiene un formato válido") @Size(max = 100, message = "El email no puede superar los 100 caracteres") String email,
        @NotBlank(message = "La contraseña es obligatoria") @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres") String password,
        @NotEmpty(message = "Debe tener al menos un rol") Set<Long> roleIds) {

}
