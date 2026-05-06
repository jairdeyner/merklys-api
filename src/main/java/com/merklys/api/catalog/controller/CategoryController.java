package com.merklys.api.catalog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merklys.api.catalog.dto.request.CreateCategoryRequest;
import com.merklys.api.catalog.dto.request.UpdateCategoryRequest;
import com.merklys.api.catalog.dto.response.CategoryResponse;
import com.merklys.api.catalog.dto.response.CategoryTreeResponse;
import com.merklys.api.catalog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'INVENTARIO')")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/tree")
    public ResponseEntity<List<CategoryTreeResponse>> getTree() {
        return ResponseEntity.ok(this.categoryService.getTree());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CreateCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoryService.createCategory(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CategoryResponse> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(this.categoryService.toggleStatus(id));
    }

}
