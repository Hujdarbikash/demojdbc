package com.bikash.person.service;

import com.bikash.person.dtos.request.EmployeeRequestDto;
import com.bikash.person.dtos.response.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {

    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto);

    public  EmployeeResponseDto getEmployeeById(long employeeId);

    public List<EmployeeResponseDto> getAllEmployee();

    public  EmployeeResponseDto updateEmployee(EmployeeRequestDto requestDto,long employeeId);

    public  void deleteEmployee(long employeeId);




}
