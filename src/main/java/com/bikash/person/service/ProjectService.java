package com.bikash.person.service;

import com.bikash.person.dtos.request.ProjectRequestDto;
import com.bikash.person.dtos.response.ProjectResponseDto;

import java.util.List;

public interface ProjectService {

    public ProjectResponseDto createProject(ProjectRequestDto requestDto);

    public ProjectResponseDto getProjectById(long projectId);

    public  List<ProjectResponseDto> getProjectByEmployeeId(long employeeId);

    public List<ProjectResponseDto> getAllProjects();

    public ProjectResponseDto assignEmployeeToProject(long projectId, long employeeId);

    public ProjectResponseDto removeEmployeeFromProject(long projectId, long employeeId);

    public void deleteProject(long projectId);



}
