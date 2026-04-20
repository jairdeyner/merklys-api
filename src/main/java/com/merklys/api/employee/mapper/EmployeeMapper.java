package com.merklys.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.merklys.api.employee.dto.request.CreateEmployeeRequest;
import com.merklys.api.employee.dto.response.EmployeeResponse;
import com.merklys.api.employee.entity.Employee;
import com.merklys.api.identity.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface EmployeeMapper {

    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Employee toEntity(CreateEmployeeRequest createEmployeeRequest);

}
