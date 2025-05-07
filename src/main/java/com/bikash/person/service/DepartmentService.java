package com.bikash.person.service;

import com.bikash.person.dtos.request.DepartmentRequestDto;
import com.bikash.person.dtos.response.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {

    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto);

    public DepartmentResponseDto getDepartmentById(Long departmentId);

    public List<DepartmentResponseDto> getAllDepartment();

    public  void deleteDepartment(long departmentId);

    public  DepartmentResponseDto  updateDepartment(DepartmentRequestDto requestDto,long departmentId);


}


