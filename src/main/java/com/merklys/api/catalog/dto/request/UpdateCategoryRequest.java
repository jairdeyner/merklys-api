package com.merklys.api.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(

        @NotBlank(message = "El nombre es obligatorio") @Size(max = 100, message = "El nombre no puede superar los 100 caracteres") String name,

        @NotBlank(message = "El slug es obligatorio") @Size(max = 100, message = "El slug no puede superar los 100 caracteres") @Pattern(regexp = "^[a-z0-9]+(-[a-z0-9]+)*$", message = "El slug solo puede contener minúsculas, números y guiones") String slug

) {
}