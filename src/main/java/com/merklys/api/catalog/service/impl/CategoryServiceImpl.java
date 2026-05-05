package com.merklys.api.catalog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merklys.api.catalog.dto.response.CategoryTreeResponse;
import com.merklys.api.catalog.mapper.CategoryMapper;
import com.merklys.api.catalog.repository.CategoryRepository;
import com.merklys.api.catalog.service.CategoryService;

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

}
