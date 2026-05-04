package com.merklys.api.employee.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.merklys.api.employee.entity.Employee;

public interface EmployeeRepositoryCustom {
    Page<Long> findAllIds(Specification<Employee> spec, Pageable pageable);
}
