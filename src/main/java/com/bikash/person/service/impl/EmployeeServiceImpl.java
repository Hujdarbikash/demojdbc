package com.bikash.person.service.impl;

import com.bikash.person.dtos.request.EmployeeRequestDto;
import com.bikash.person.dtos.response.EmployeeResponseDto;
import com.bikash.person.enums.EmailTemplate;
import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.exceptions.ResourceNotFoundException;
import com.bikash.person.mappers.EmployeeMapper;
import com.bikash.person.models.Employee;
import com.bikash.person.repositories.DepartmentRepository;
import com.bikash.person.repositories.customRepositories.EmployeeCustomRepository;
import com.bikash.person.service.EmailService;
import com.bikash.person.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeCustomRepository repository;
    private  final EmailService emailService;
    private  final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeCustomRepository repository, EmailService emailService, DepartmentRepository departmentRepository) {
        this.repository = repository;
        this.emailService = emailService;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {

        if (this.repository.existByEmail(requestDto.getEmail()))
            throw new DatabaseOperationException("User already exists with email: "+requestDto.getEmail());

        if (!this.departmentRepository.existById(requestDto.getDepartment().getDepartmentId()))
            throw new ResourceNotFoundException("Department", "departmentId", requestDto.getDepartment().getDepartmentId());

        try {
            Employee savedEmployee = this.repository.createEmployee(EmployeeMapper.toEntity(requestDto), requestDto.getDepartment().getDepartmentId());
            System.out.println(savedEmployee);

            Context context = new Context();
            context.setVariable("employee",savedEmployee);
            this.emailService.sendEmailWithTemplate(savedEmployee.getEmail(),"Welcome Email","welcome", EmailTemplate.WELCOME,context);

            System.out.println(" running  sync");

            return EmployeeMapper.toResponse(savedEmployee);

        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to save employee");
        }

    }

    @Override
    public EmployeeResponseDto getEmployeeById(long employeeId) {
        try {
            return EmployeeMapper.toResponse(this.repository.getEmployeeById(employeeId));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Employee", "employeeId", employeeId);
        }
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployee() {
        try {
            List<EmployeeResponseDto> employeeResponseList = this.repository.getAllEmployee().stream().map(e -> EmployeeMapper.toResponse(e)).collect(Collectors.toList());
            return employeeResponseList;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to retrieve employees list");
        }
    }

    @Override
    public EmployeeResponseDto updateEmployee(EmployeeRequestDto requestDto, long employeeId) {

        if (!this.repository.existById(employeeId)) throw new ResourceNotFoundException("Employee", "employeeId", employeeId);
     try {
         return  EmployeeMapper.toResponse(this.repository.updateEmployee(EmployeeMapper.toEntity(requestDto),employeeId));
     }catch (Exception e)
     {
         throw new DatabaseOperationException("Failed to update Employee");
     }

    }

    @Override
    public void deleteEmployee(long employeeId) {

        if (!this.repository.existById(employeeId)) throw new ResourceNotFoundException("Employee", "employeeId", employeeId);
        try {
            this.repository.deleteEmployee(employeeId);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to delete employee with employeeId: "+employeeId);
        }
    }


}
