package com.merklys.api.catalog.dto.response;

public record CategoryResponse(
        Long id,
        String name,
        String slug,
        boolean isActive) {

}
