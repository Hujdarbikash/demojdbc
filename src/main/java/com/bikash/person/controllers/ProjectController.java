package com.bikash.person.controllers;

import com.bikash.person.dtos.request.ProjectRequestDto;
import com.bikash.person.dtos.response.ProjectResponseDto;
import com.bikash.person.globalresponse.RestResponse;
import com.bikash.person.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("v1/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping()
    public ResponseEntity<?> create( @Valid @RequestBody ProjectRequestDto requestDto) {

        ProjectResponseDto project = this.projectService.createProject(requestDto);
        return new RestResponse<>().createdWithPayload(project, "Project created successfully");

    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectByProjectId(@PathVariable("projectId") long projectId) {
        ProjectResponseDto project = this.projectService.getProjectById(projectId);
        return new RestResponse<>().okWithPayload(project, "project fetched successfully");

    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getProjectsByEmployeeId(@PathVariable("employeeId") long employeeId) {
        List<ProjectResponseDto> projects = this.projectService.getProjectByEmployeeId(employeeId);
        return new RestResponse<>().okWithPayload(projects, "Post fetched successfully");
    }

    @GetMapping()
    public ResponseEntity<?> allProjects() {
        List<ProjectResponseDto> projects = this.projectService.getAllProjects();
        return new RestResponse<>().okWithPayload(projects, "Post fetched successfully");
    }


    @PostMapping("assign/project-id/{projectId}/employee-id/{employeeId}")
    public ResponseEntity<?> assignEmployee(@PathVariable("projectId") long projectId,
                                            @PathVariable("employeeId") long employeeId) {

        ProjectResponseDto project = this.projectService.assignEmployeeToProject(projectId, employeeId);
        return new RestResponse<>().okWithPayload(project, "Employee assigned to project");
    }



    @PostMapping("remove/employee-id/{employeeId}/project-id/{projectId}")
    public ResponseEntity<?> removeEmployee(@PathVariable("employeeId") long employeeId,
                                            @PathVariable("projectId") long projectId) {
        ProjectResponseDto project = this.projectService.removeEmployeeFromProject(projectId, employeeId);
        return new RestResponse<>().okWithPayload(project, "Employee Removed from project");
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> delete(@PathVariable("projectId") long projectId) {
             this.projectService.deleteProject(projectId);
        return new RestResponse<>().okWithPayload(true, "project deleted successfully");
    }


}

