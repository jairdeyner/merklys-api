package com.merklys.api.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.merklys.api.identity.dto.request.RoleRequest;
import com.merklys.api.identity.dto.response.RoleResponse;
import com.merklys.api.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    Role toEntity(RoleRequest roleRequest);

    RoleResponse toResponse(Role role);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(RoleRequest roleRequest, @MappingTarget Role role);

}
