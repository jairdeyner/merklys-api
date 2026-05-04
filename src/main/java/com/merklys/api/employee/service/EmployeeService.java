package com.merklys.api.employee.service;

import org.springframework.data.domain.Pageable;

import com.merklys.api.common.response.PagedResponse;
import com.merklys.api.employee.dto.request.CreateEmployeeRequest;
import com.merklys.api.employee.dto.request.EmployeeFilterRequest;
import com.merklys.api.employee.dto.response.EmployeeResponse;
import com.merklys.api.employee.dto.response.EmployeeSummaryResponse;

public interface EmployeeService {

    EmployeeResponse create(CreateEmployeeRequest createEmployeeRequest);

    PagedResponse<EmployeeSummaryResponse> findAll(EmployeeFilterRequest filters, Pageable pageable);

}
