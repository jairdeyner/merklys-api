package com.merklys.api.catalog.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.merklys.api.catalog.entity.Category;
import com.merklys.api.catalog.dto.response.CategoryResponse;
import com.merklys.api.catalog.dto.response.CategoryTreeResponse;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "isActive", source = "active")
    CategoryResponse toResponse(Category category);

    @Mapping(target = "isActive", source = "active")
    CategoryTreeResponse toTreeResponse(Category category);

    List<CategoryTreeResponse> toTreeResponseList(List<Category> categories);
}
