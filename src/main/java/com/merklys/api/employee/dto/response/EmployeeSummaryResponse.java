package com.merklys.api.employee.dto.response;

import java.util.Set;

public record EmployeeSummaryResponse(Long id,
        String firstName,
        String lastName,
        String dni,
        String position,
        String username,
        Set<String> roles,
        boolean isActive,
        boolean isLocked) {

}
