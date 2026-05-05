package com.merklys.api.catalog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merklys.api.catalog.dto.response.CategoryTreeResponse;
import com.merklys.api.catalog.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'INVENTARIO')")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/tree")
    public ResponseEntity<List<CategoryTreeResponse>> getAllCategories() {
        return ResponseEntity.ok(this.categoryService.getTree());
    }

}
