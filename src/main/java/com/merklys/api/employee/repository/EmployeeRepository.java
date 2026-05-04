package com.merklys.api.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.merklys.api.employee.entity.Employee;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>, EmployeeRepositoryCustom {

    boolean existsByDni(String dni);

    @EntityGraph(attributePaths = { "user", "user.roles" })
    @Query("SELECT e FROM Employee e WHERE e.id IN :ids")
    List<Employee> findAllWithRolesByIds(List<Long> ids);
}
