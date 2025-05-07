package com.bikash.person.mappers;

import com.bikash.person.dtos.request.DepartmentRequestDto;
import com.bikash.person.dtos.response.DepartmentResponseDto;
import com.bikash.person.models.Department;

public class DepartmentMapper {
    public  static Department toEntity(DepartmentRequestDto request)
    {
        Department department = new Department();
        department.setDepartmentName(request.getDepartmentName());
        return  department;
    }

    public  static DepartmentResponseDto toResponse(Department department)
    {
        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
        departmentResponseDto.setDepartmentId(department.getDepartmentId());
        departmentResponseDto.setDepartmentName(department.getDepartmentName());
        return  departmentResponseDto;
    }

}
