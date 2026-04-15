package com.merklys.api.identity.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merklys.api.common.exception.DuplicateResourceException;
import com.merklys.api.common.exception.ResourceNotFoundException;
import com.merklys.api.identity.dto.request.RoleRequest;
import com.merklys.api.identity.dto.response.RoleResponse;
import com.merklys.api.identity.entity.Role;
import com.merklys.api.identity.mapper.RoleMapper;
import com.merklys.api.identity.repository.RoleRepository;
import com.merklys.api.identity.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> findAll() {
        return this.roleRepository.findAll().stream().map(this.roleMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse findById(Long id) {
        Role existingRole = this.findRoleById(id);

        return this.roleMapper.toResponse(existingRole);
    }

    @Override
    @Transactional
    public RoleResponse create(RoleRequest request) {
        String name = request.name().toUpperCase().trim();

        if (this.roleRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Ya existe un rol con el nombre: " + name);
        }

        Role role = this.roleMapper.toEntity(request);
        role.setName(name);

        return this.roleMapper.toResponse(this.roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleResponse update(Long id, RoleRequest request) {
        Role existingRole = this.findRoleById(id);

        String name = request.name().toUpperCase().trim();

        boolean nameChanged = !existingRole.getName().equalsIgnoreCase(name);

        if (nameChanged && this.roleRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Ya existe un rol con el nombre: " + name);
        }

        this.roleMapper.updateEntityFromRequest(request, existingRole);
        existingRole.setName(name);

        return this.roleMapper.toResponse(this.roleRepository.save(existingRole));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Role existingRole = this.findRoleById(id);

        this.roleRepository.delete(existingRole);
    }

    private Role findRoleById(Long id) {
        return this.roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con el id: " + id));
    }

}
