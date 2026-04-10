package com.merklys.api.auth.service;

import java.util.List;

import com.merklys.api.auth.dto.request.RoleRequest;
import com.merklys.api.auth.dto.response.RoleResponse;

public interface RoleService {
    List<RoleResponse> findAll();

    RoleResponse findById(Long id);

    RoleResponse create(RoleRequest request);

    RoleResponse update(Long id, RoleRequest request);

    void delete(Long id);
}
