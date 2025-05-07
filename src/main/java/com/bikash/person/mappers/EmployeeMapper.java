package com.bikash.person.mappers;

import com.bikash.person.dtos.request.EmployeeRequestDto;
import com.bikash.person.dtos.response.EmployeeResponseDto;
import com.bikash.person.models.Employee;

import java.util.stream.Collectors;

public class EmployeeMapper {
    public  static Employee toEntity(EmployeeRequestDto requestDto){
        Employee employee = new Employee();
        employee.setName(requestDto.getName());
        employee.setDepartment(requestDto.getDepartment());
        employee.setEmail(requestDto.getEmail());
        return  employee;
    }


    public  static EmployeeResponseDto toResponse(Employee employee){

        EmployeeResponseDto response = new EmployeeResponseDto();
        response.setEmployeeId(employee.getEmployeeId());
        response.setEmail(employee.getEmail());
        response.setName(employee.getName());
        response.setDepartment(DepartmentMapper.toResponse(employee.getDepartment()));
        response.setAddresses(employee.getAddresses().stream().map(address->AddressMapper.toResponse(address)).collect(Collectors.toList()));
        response.setPosts(employee.getPosts());
        response.setProjects(employee.getProjects().stream().map(e->ProjectMapper.toResponse(e)).collect(Collectors.toList()));
        return  response;

    };

}
