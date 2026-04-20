package com.merklys.api.identity.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merklys.api.identity.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    List<Role> findByIdIn(Set<Long> ids);
}
