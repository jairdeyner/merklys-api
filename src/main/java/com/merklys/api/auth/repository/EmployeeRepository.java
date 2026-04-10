package com.merklys.api.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merklys.api.auth.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
