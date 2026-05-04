package com.merklys.api.employee.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.merklys.api.employee.dto.request.CreateEmployeeRequest;
import com.merklys.api.employee.dto.response.EmployeeResponse;
import com.merklys.api.employee.dto.response.EmployeeSummaryResponse;
import com.merklys.api.employee.entity.Employee;
import com.merklys.api.identity.entity.Role;
import com.merklys.api.identity.entity.User;
import com.merklys.api.identity.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface EmployeeMapper {

    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Employee toEntity(CreateEmployeeRequest createEmployeeRequest);

    default EmployeeSummaryResponse toSummaryResponse(Employee employee) {
        User user = employee.getUser();

        Set<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new EmployeeSummaryResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDni(),
                employee.getPosition(),
                user.getUsername(),
                roles,
                user.isActive(),
                !user.isAccountNonLocked());
    }
}
