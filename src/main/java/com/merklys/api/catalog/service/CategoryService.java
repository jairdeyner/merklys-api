package com.merklys.api.catalog.service;

import java.util.List;

import com.merklys.api.catalog.dto.request.CreateCategoryRequest;
import com.merklys.api.catalog.dto.request.UpdateCategoryRequest;
import com.merklys.api.catalog.dto.response.CategoryResponse;
import com.merklys.api.catalog.dto.response.CategoryTreeResponse;

public interface CategoryService {
    List<CategoryTreeResponse> getTree();

    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);

    CategoryResponse toggleStatus(Long id);
}
