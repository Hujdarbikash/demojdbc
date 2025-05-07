package com.bikash.person.controllers;

import com.bikash.person.dtos.request.DepartmentRequestDto;
import com.bikash.person.dtos.response.DepartmentResponseDto;
import com.bikash.person.globalresponse.RestResponse;
import com.bikash.person.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @PostMapping("")
    public ResponseEntity<?> createDepartment( @Valid @RequestBody DepartmentRequestDto requestDto) {
        DepartmentResponseDto department = this.departmentService.createDepartment(requestDto);
        return new RestResponse<>().createdWithPayload(department, "Department created successfully");
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<?> getDepartmentById(@PathVariable("departmentId") long departmentId) {
        DepartmentResponseDto department = this.departmentService.getDepartmentById(departmentId);
        return new RestResponse<>().okWithPayload(department, "Department fetched successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllDepartments() {
        List<DepartmentResponseDto> lists = this.departmentService.getAllDepartment();
        return new RestResponse<>().okWithPayload(lists, "Departments fetched successfully");
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<?> delete(@PathVariable("departmentId") long departmentId) {
//        this.departmentService.deleteDepartment(departmentId);
        return new RestResponse<>().error(null,"Not allowed to delete department", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<?> update( @Valid @RequestBody DepartmentRequestDto requestDto,
                                    @PathVariable("departmentId") long departmentId) {
        DepartmentResponseDto department = this.departmentService.updateDepartment(requestDto,departmentId);
        return new RestResponse<>().createdWithPayload(department, "Department updated successfully");
    }



}
