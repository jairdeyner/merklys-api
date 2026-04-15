package com.merklys.api.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merklys.api.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
