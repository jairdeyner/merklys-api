package com.merklys.api.employee.service;

import com.merklys.api.employee.dto.request.CreateEmployeeRequest;
import com.merklys.api.employee.dto.response.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse create(CreateEmployeeRequest createEmployeeRequest);

}
