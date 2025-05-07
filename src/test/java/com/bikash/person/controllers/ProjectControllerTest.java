package com.bikash.person.controllers;

import com.bikash.person.dtos.request.ProjectRequestDto;
import com.bikash.person.dtos.response.ProjectResponseDto;
import com.bikash.person.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProjectService projectService;


    private ProjectRequestDto request;

    private ProjectResponseDto response;

    private List<ProjectResponseDto> responseList = new ArrayList<>();


    @BeforeEach
    void  setUp()
    {
        request = new ProjectRequestDto();
        request.setProjectName("ASD");



        response = new ProjectResponseDto();
        response.setProjectId(100l);
        List<Long>employeeIdsList  = new ArrayList<>();
        employeeIdsList.add(1l);
        employeeIdsList.add(100l);
        response.setEmployeeIds(employeeIdsList);
        response.setProjectName("ASD");



        responseList.add(response);

    }

    @AfterEach
    void tearUp()
    {
        responseList = null;
        response= null;
        request = null;

    }

    @Test
    void create() throws Exception {

        when(this.projectService.createProject(any(ProjectRequestDto.class))).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.projectId").value(response.getProjectId()))
                .andExpect(jsonPath("$.data.projectName").value(response.getProjectName()));


    }

    @Test
    void getProjectByProjectId()  throws  Exception{

        when(this.projectService.getProjectById(any(Long.class))).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/project/{projectId}",1l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(response.getProjectId()))
                .andExpect(jsonPath("$.data.projectName").value(response.getProjectName()));
    }

    @Test
    void getProjectsByEmployeeId() throws  Exception {

        when(this.projectService.getProjectByEmployeeId(any(Long.class))).thenReturn(responseList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/project/employee/{emnployeeId}",1l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].projectId").value(responseList.get(0).getProjectId()))
                .andExpect(jsonPath("$.data.[0].projectName").value(responseList.get(0).getProjectName()));

    }

    @Test
    void allProjects() throws  Exception{

        when(this.projectService.getAllProjects()).thenReturn(responseList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/project",1l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].projectId").value(responseList.get(0).getProjectId()))
                .andExpect(jsonPath("$.data.[0].projectName").value(responseList.get(0).getProjectName()));

    }

    @Test
    void assignEmployee() throws  Exception{

        when(this.projectService.assignEmployeeToProject(anyLong(),anyLong())).thenReturn(response);

        when(this.projectService.getProjectById(any(Long.class))).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/project/assign/project-id/{projectId}/employee-id/{employeeId}",10l,1l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(response.getProjectId()))
                .andExpect(jsonPath("$.data.projectName").value(response.getProjectName()));


    }

    @Test
    void removeEmployee() throws  Exception {

        when(this.projectService.removeEmployeeFromProject(anyLong(),anyLong())).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/project/remove/employee-id/{employeeId}/project-id/{projectId}",1l,100l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(response.getProjectId()))
                .andExpect(jsonPath("$.data.projectName").value(response.getProjectName()));
    }

    @Test
    void delete() throws  Exception{

        doNothing().when(this.projectService).deleteProject(anyLong());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/project/{projectId}",1l))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath(("$.message")).value("project deleted successfully"));


    }
}