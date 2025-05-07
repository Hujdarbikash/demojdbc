package com.bikash.person.controllers;

import com.bikash.person.dtos.request.EmployeeRequestDto;
import com.bikash.person.dtos.response.DepartmentResponseDto;
import com.bikash.person.dtos.response.EmployeeResponseDto;
import com.bikash.person.models.Department;
import com.bikash.person.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {


    private EmployeeResponseDto response;
    private Department department;

    private DepartmentResponseDto departmentResponseDto;
    private EmployeeRequestDto request;
    private List<EmployeeResponseDto> responseList = new ArrayList<>();

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        department = new Department();
        department.setDepartmentName("BCE");
        department.setDepartmentId(100l);


        request = new EmployeeRequestDto();
        request.setDepartment(department);
        request.setName("Aayush");
        request.setEmail("hujdarbikash000@gmail.com");


        departmentResponseDto = new DepartmentResponseDto();
        departmentResponseDto.setDepartmentId(100l);
        departmentResponseDto.setDepartmentName("BCE");

        response = new EmployeeResponseDto();
        response.setDepartment(departmentResponseDto);
        response.setName("Aayush");
        response.setEmployeeId(50l);
        response.setEmail(request.getEmail());


        responseList.add(response);


    }

    @AfterEach
    void tearUp() {
        response = null;
        responseList = null;
        request = null;
        department = null;
        departmentResponseDto = null;

    }


    @Test
    void createEmployee() throws Exception {

        when(this.employeeService.createEmployee(any(EmployeeRequestDto.class))).thenReturn(response);


        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(response.getName()));

    }

    @Test
    void getEmployeeById() throws Exception {

        when(this.employeeService.getEmployeeById(any(Long.class))).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/employee/{employeeId}", 50l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(response.getName()));
    }

    @Test
    void getAllEmployee() throws Exception {
        OngoingStubbing<List<EmployeeResponseDto>> listOngoingStubbing = when(this.employeeService.getAllEmployee()).thenReturn(responseList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].name").value(responseList.get(0).getName()));
    }

    @Test
    void update()  throws Exception{

        when(this.employeeService.updateEmployee(any(EmployeeRequestDto.class),any(Long.class))).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/v1/employee/{employeeId}",1l)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(response.getName()));
    }

    @Test
    void delete() throws Exception {

        doNothing().when(this.employeeService).deleteEmployee(any(Long.class));

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/employee/{employeeId}", 50l))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Not allowed to delete employee"));

    }
}