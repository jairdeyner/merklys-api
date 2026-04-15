package com.merklys.api.identity.service;

import java.util.List;

import com.merklys.api.identity.dto.request.RoleRequest;
import com.merklys.api.identity.dto.response.RoleResponse;

public interface RoleService {
    List<RoleResponse> findAll();

    RoleResponse findById(Long id);

    RoleResponse create(RoleRequest request);

    RoleResponse update(Long id, RoleRequest request);

    void delete(Long id);
}
