package com.merklys.api.catalog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merklys.api.catalog.dto.request.CreateCategoryRequest;
import com.merklys.api.catalog.dto.request.UpdateCategoryRequest;
import com.merklys.api.catalog.dto.response.CategoryResponse;
import com.merklys.api.catalog.dto.response.CategoryTreeResponse;
import com.merklys.api.catalog.entity.Category;
import com.merklys.api.catalog.mapper.CategoryMapper;
import com.merklys.api.catalog.repository.CategoryRepository;
import com.merklys.api.catalog.service.CategoryService;
import com.merklys.api.common.exception.BusinessException;
import com.merklys.api.common.exception.DuplicateResourceException;
import com.merklys.api.common.exception.ResourceNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryTreeResponse> getTree() {

        return this.categoryMapper.toTreeResponseList(this.categoryRepository.findRootsWithChildren());
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category parent = this.categoryRepository.findById(request.parentId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría padre no encontrada"));

        if (parent.isRoot() == false) {
            throw new BusinessException(
                    "No se pueden crear subcategorías de tercer nivel. Solo se permiten 2 niveles.");
        }

        if (this.categoryRepository.existsBySlug(request.slug())) {
            throw new DuplicateResourceException("El slug '" + request.slug() + "' ya está en uso");
        }

        if (this.categoryRepository.existsByNameAndParentId(request.name(), request.parentId())) {
            throw new DuplicateResourceException(
                    "Ya existe una subcategoría con el nombre '" + request.name() + "' bajo esta categoría");
        }

        Category category = this.categoryMapper.toEntity(request);
        category.setParent(parent);

        return this.categoryMapper.toResponse(this.categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        if (category.isRoot()) {
            throw new BusinessException("Las categorías raíz no pueden modificarse");
        }

        if (categoryRepository.existsBySlugAndIdNot(request.slug(), id)) {
            throw new DuplicateResourceException("El slug '" + request.slug() + "' ya está en uso");
        }

        if (categoryRepository.existsByNameAndParentIdAndIdNot(request.name(), category.getParent().getId(), id)) {
            throw new DuplicateResourceException(
                    "Ya existe una subcategoría con el nombre '" + request.name() + "' bajo esta categoría");
        }

        category.setName(request.name());
        category.setSlug(request.slug());

        return this.categoryMapper.toResponse(this.categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse toggleStatus(Long id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        if (category.isRoot()) {
            throw new BusinessException("Las categorías raíz no pueden desactivarse");
        }

        category.setActive(!category.isActive());

        return this.categoryMapper.toResponse(this.categoryRepository.save(category));
    }

}
