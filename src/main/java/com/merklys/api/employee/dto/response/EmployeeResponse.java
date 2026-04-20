package com.merklys.api.employee.dto.response;

import com.merklys.api.identity.dto.response.UserResponse;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String phone,
        String dni,
        String position,
        UserResponse user) {

}
