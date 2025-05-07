package com.bikash.person.controllers;

import com.bikash.person.dtos.request.EmployeeRequestDto;
import com.bikash.person.dtos.response.EmployeeResponseDto;
import com.bikash.person.globalresponse.RestResponse;
import com.bikash.person.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping()
    public ResponseEntity<?> createEmployee( @Valid  @RequestBody  EmployeeRequestDto requestDto) {
        EmployeeResponseDto savedEmployee = this.employeeService.createEmployee(requestDto);

        return new RestResponse<>().createdWithPayload(savedEmployee, "Employee Created Successfully");
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("employeeId") long employeeId)
    {
        EmployeeResponseDto employee = this.employeeService.getEmployeeById(employeeId);
        return new RestResponse<>().okWithPayload(employee,"Employee  fetched successfully");
    }

    @GetMapping()
    public ResponseEntity<?> getAllEmployee()
    {
        this.employeeService.getAllEmployee();
        return new RestResponse<>().okWithPayload( this.employeeService.getAllEmployee(),"Employee  fetched successfully");
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> update(@Valid @RequestBody EmployeeRequestDto requestDto,@PathVariable("employeeId") long employeeId)
    {
        EmployeeResponseDto employee = this.employeeService.updateEmployee(requestDto,employeeId);
        return new RestResponse<>().createdWithPayload(employee,"Employee update successfully");
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable("employeeId") long employeeId)
    {
       // this.employeeService.deleteEmployee(employeeId);
        return new RestResponse<>().error( null,"Not allowed to delete employee", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
