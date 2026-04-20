package com.merklys.api.employee.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merklys.api.common.exception.DuplicateResourceException;
import com.merklys.api.employee.dto.request.CreateEmployeeRequest;
import com.merklys.api.employee.dto.response.EmployeeResponse;
import com.merklys.api.employee.entity.Employee;
import com.merklys.api.employee.mapper.EmployeeMapper;
import com.merklys.api.employee.repository.EmployeeRepository;
import com.merklys.api.employee.service.EmployeeService;
import com.merklys.api.identity.dto.request.CreateUserRequest;
import com.merklys.api.identity.entity.User;
import com.merklys.api.identity.service.UserService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UserService userService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
            UserService userService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.userService = userService;
    }

    @Override
    @Transactional
    public EmployeeResponse create(CreateEmployeeRequest createEmployeeRequest) {

        if (this.employeeRepository.existsByDni(createEmployeeRequest.dni())) {
            throw new DuplicateResourceException("El DNI " + createEmployeeRequest.dni() + " ya está registrado");
        }

        CreateUserRequest createUserRequest = new CreateUserRequest(
                createEmployeeRequest.username(),
                createEmployeeRequest.email(),
                createEmployeeRequest.password(),
                createEmployeeRequest.roleIds());

        User user = this.userService.createEntity(createUserRequest);

        Employee employee = this.employeeMapper.toEntity(createEmployeeRequest);
        employee.setUser(user);

        Employee savedEmployee = this.employeeRepository.save(employee);

        return this.employeeMapper.toResponse(savedEmployee);
    }

}
