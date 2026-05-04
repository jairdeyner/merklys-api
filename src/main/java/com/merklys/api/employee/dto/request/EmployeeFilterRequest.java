package com.merklys.api.employee.dto.request;

public record EmployeeFilterRequest(
        String search,
        String role,
        Boolean isActive,
        Boolean isLocked) {

}