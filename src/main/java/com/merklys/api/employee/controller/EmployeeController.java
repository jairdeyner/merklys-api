package com.merklys.api.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.merklys.api.employee.dto.request.CreateEmployeeRequest;
import com.merklys.api.employee.dto.response.EmployeeResponse;
import com.merklys.api.employee.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/employees")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(
            @Valid @RequestBody CreateEmployeeRequest createEmployeeRequest) {

        EmployeeResponse response = this.employeeService.create(createEmployeeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
