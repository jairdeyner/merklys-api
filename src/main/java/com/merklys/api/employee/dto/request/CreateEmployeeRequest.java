package com.merklys.api.employee.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateEmployeeRequest(

        @NotBlank(message = "El nombre es obligatorio") @Size(max = 100, message = "El nombre no puede superar los 100 caracteres") String firstName,

        @NotBlank(message = "El apellido es obligatorio") @Size(max = 100, message = "El apellido no puede superar los 100 caracteres") String lastName,

        @Pattern(regexp = "^9[0-9]{8}$", message = "El teléfono debe iniciar con 9 y tener 9 dígitos") String phone,

        @NotBlank(message = "El DNI es obligatorio") @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe tener exactamente 8 dígitos") String dni,

        @NotBlank(message = "El cargo es obligatorio") @Size(max = 100, message = "El cargo no puede superar los 100 caracteres") String position,

        @NotBlank(message = "El nombre de usuario es obligatorio") @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres") String username,

        @NotBlank(message = "El email es obligatorio") @Email(message = "El email no tiene un formato válido") @Size(max = 100, message = "El email no puede superar los 100 caracteres") String email,

        @NotBlank(message = "La contraseña es obligatoria") @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres") String password,

        @NotEmpty(message = "Debe tener al menos un rol") Set<Long> roleIds) {

}
