package com.merklys.api.employee.controller;

import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.merklys.api.common.exception.InvalidSortFieldException;
import com.merklys.api.common.response.PagedResponse;
import com.merklys.api.employee.dto.request.CreateEmployeeRequest;
import com.merklys.api.employee.dto.request.EmployeeFilterRequest;
import com.merklys.api.employee.dto.response.EmployeeResponse;
import com.merklys.api.employee.dto.response.EmployeeSummaryResponse;
import com.merklys.api.employee.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/employees")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class EmployeeController {

    private static final Set<String> SORTABLE_FIELDS = Set.of(
            "firstName", "lastName", "dni", "position");

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

    @GetMapping
    public ResponseEntity<PagedResponse<EmployeeSummaryResponse>> findAll(
            @ModelAttribute EmployeeFilterRequest filters,
            @PageableDefault(size = 10, page = 0, sort = "lastName") Pageable pageable) {

        pageable.getSort().forEach(order -> {
            if (!SORTABLE_FIELDS.contains(order.getProperty())) {
                throw new InvalidSortFieldException(order.getProperty(), SORTABLE_FIELDS);
            }
        });

        PagedResponse<EmployeeSummaryResponse> response = this.employeeService.findAll(filters, pageable);
        return ResponseEntity.ok(response);
    }

}
