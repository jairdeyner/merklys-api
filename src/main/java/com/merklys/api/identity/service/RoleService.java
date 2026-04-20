package com.merklys.api.identity.service;

import java.util.List;
import java.util.Set;

import com.merklys.api.identity.dto.request.RoleRequest;
import com.merklys.api.identity.dto.response.RoleResponse;
import com.merklys.api.identity.entity.Role;

public interface RoleService {
    List<RoleResponse> findAll();

    RoleResponse findById(Long id);

    RoleResponse create(RoleRequest request);

    RoleResponse update(Long id, RoleRequest request);

    void delete(Long id);

    List<Role> findRolesByIds(Set<Long> ids);
}
