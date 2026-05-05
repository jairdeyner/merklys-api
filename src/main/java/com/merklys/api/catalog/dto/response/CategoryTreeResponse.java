package com.merklys.api.catalog.dto.response;

import java.util.List;

public record CategoryTreeResponse(
        Long id,
        String name,
        String slug,
        boolean isActive,
        List<CategoryResponse> children) {

}
