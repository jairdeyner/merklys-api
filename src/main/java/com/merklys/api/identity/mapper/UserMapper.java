package com.merklys.api.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.merklys.api.identity.dto.request.CreateUserRequest;
import com.merklys.api.identity.dto.response.UserResponse;
import com.merklys.api.identity.entity.User;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(CreateUserRequest createUserRequest);

    @Mapping(target = "isActive", source = "active")
    UserResponse toResponse(User user);

}
