package com.merklys.api.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merklys.api.identity.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByNameIgnoreCase(String name);
}
