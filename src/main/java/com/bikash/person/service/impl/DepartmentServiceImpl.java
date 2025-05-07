package com.bikash.person.service.impl;

import com.bikash.person.dtos.request.DepartmentRequestDto;
import com.bikash.person.dtos.response.DepartmentResponseDto;
import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.exceptions.ResourceNotFoundException;
import com.bikash.person.mappers.DepartmentMapper;
import com.bikash.person.repositories.DepartmentRepository;
import com.bikash.person.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        if (departmentRepository.existByName(requestDto.getDepartmentName()))
            throw new DatabaseOperationException("Department already exist with name:"+requestDto.getDepartmentName());
        try {
            return DepartmentMapper.toResponse(this.departmentRepository.createDepartment(DepartmentMapper.toEntity(requestDto)));
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to save department :" + e.getMessage());
        }
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long departmentId) {
        try {
            return DepartmentMapper.toResponse(this.departmentRepository.getDepartmentById(departmentId));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Department", "departmentId", departmentId);
        }
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartment() {
        try {
            return this.departmentRepository.getAllDepartment().stream().map(e -> DepartmentMapper.toResponse(e))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to retrieve list from database");
        }
    }

    @Override
    public void deleteDepartment(long departmentId) {

        if (!this.departmentRepository.existById(departmentId))
            throw new ResourceNotFoundException("Department", "departmentId", departmentId);
        try {
            this.departmentRepository.deleteDepartment(departmentId);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to delete department with departmentId: " + departmentId);
        }
    }

    @Override
    public DepartmentResponseDto updateDepartment(DepartmentRequestDto requestDto, long departmentId) {

        if (!this.departmentRepository.existById(departmentId))
            throw new ResourceNotFoundException("Department", "departmentId", departmentId);
        try {
            return DepartmentMapper.toResponse(this.departmentRepository.updateDepartment(DepartmentMapper.toEntity(requestDto), departmentId));
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update department with departmentId: " + departmentId);
        }
    }
}
