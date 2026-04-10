package com.merklys.api.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merklys.api.auth.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByNameIgnoreCase(String name);
}
