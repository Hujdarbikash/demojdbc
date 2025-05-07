package com.bikash.person.controllers;

import com.bikash.person.dtos.request.DepartmentRequestDto;
import com.bikash.person.dtos.response.DepartmentResponseDto;
import com.bikash.person.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {


    private DepartmentRequestDto request;

    private DepartmentResponseDto response;
    private DepartmentResponseDto response2;
    private List<DepartmentResponseDto> responseList = new ArrayList<>();


    @BeforeEach
    void initData() {
        request = new DepartmentRequestDto();
        request.setDepartmentName("BAG");

        response = new DepartmentResponseDto();
        response.setDepartmentId(12l);
        response.setDepartmentName("BAG");

        response2 = new DepartmentResponseDto();
        response2.setDepartmentId(30l);
        response2.setDepartmentName("BCT");

        responseList.add(response);


    }

    @AfterEach
    void tearUp() {
        request = null;
        response = null;
        responseList = null;
    }


    @MockitoBean
    private DepartmentService departmentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private static RestTemplate restTemplate;


    @Test
    @DisplayName("createDepartmentTest")
    void createDepartment() throws Exception {

        Mockito.when(this.departmentService.createDepartment(Mockito.any(DepartmentRequestDto.class))).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.departmentName").value(response.getDepartmentName()));
    }

    @Test
    @DisplayName("getDepartmentByIdTest")
    void getDepartmentById() throws Exception {

        when(this.departmentService.getDepartmentById(any(Long.class))).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/department/{departmentId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.departmentName").value(response.getDepartmentName()));

    }

    @Test
    @DisplayName("getAllDepartmentsTest")
    void getAllDepartments() throws Exception {

        when(this.departmentService.getAllDepartment()).thenReturn(responseList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/department/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].departmentId").value(12))
                .andExpect(jsonPath("$.data[0].departmentName").value("BAG"));

    }

    @Test
    @DisplayName("deleteTest")
    void delete() throws Exception {

        doNothing().when(departmentService).deleteDepartment(any(Long.class));

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/department/{departmentId}", 1))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Not allowed to delete department"));

    }

    @Test
    @DisplayName("UpdateTest")
    void update() throws Exception {

        when(this.departmentService.updateDepartment(any(DepartmentRequestDto.class), any(Long.class))).thenReturn(response2);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/v1/department/{departmentId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value(response2));

    }
}
