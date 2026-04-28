package com.merklys.api.common.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

@Validated
@ConfigurationProperties(prefix = "app.cors")
public record CorsProperties(

        @NotEmpty(message = "Allowed origins no puede estar vacio") List<String> allowedOrigins,

        @NotEmpty(message = "Allowed methods no puede estar vacio") List<String> allowedMethods,

        @NotEmpty(message = "Allowed headers no puede estar vacio") List<String> allowedHeaders,

        boolean allowCredentials,

        @Positive(message = "Max age debe ser mayor a 0") long maxAge) {

    public CorsProperties {
        allowedOrigins = List.copyOf(allowedOrigins);
        allowedMethods = List.copyOf(allowedMethods);
        allowedHeaders = List.copyOf(allowedHeaders);
    }
}
