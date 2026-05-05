package com.merklys.api.catalog.service;

import java.util.List;

import com.merklys.api.catalog.dto.response.CategoryTreeResponse;

public interface CategoryService {
    List<CategoryTreeResponse> getTree();
}
