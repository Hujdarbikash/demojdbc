package com.bikash.person.service.impl;

import com.bikash.person.dtos.request.ProjectRequestDto;
import com.bikash.person.dtos.response.ProjectResponseDto;
import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.exceptions.ResourceNotFoundException;
import com.bikash.person.mappers.ProjectMapper;
import com.bikash.person.models.Project;
import com.bikash.person.repositories.EmployeeRepository;
import com.bikash.person.repositories.customRepositories.ProjectCustomRepository;
import com.bikash.person.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectCustomRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public ProjectServiceImpl(ProjectCustomRepository projectRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto requestDto) {
        if (this.projectRepository.existByName(requestDto.getProjectName().toUpperCase()))
            throw new DatabaseOperationException("Project already exist with name :"+requestDto.getProjectName());
        try {
            return ProjectMapper.toResponse(this.projectRepository.createProject(ProjectMapper.toEntity(requestDto)));
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to save project in database");
        }

    }

    @Override
    public ProjectResponseDto getProjectById(long projectId) {
        try {
            return ProjectMapper.toResponse(this.projectRepository.getProjectById(projectId));
        } catch (Exception e) {
            throw new ResourceNotFoundException("project","projectId",projectId);
        }
    }

    @Override
    public List<ProjectResponseDto> getProjectByEmployeeId(long employeeId) {
        if (!this.employeeRepository.existById(employeeId))
            throw new ResourceNotFoundException("Employee", "employeeId", employeeId);

        try {
            return this.projectRepository.getProjectByEmployeeId(employeeId).stream().map(e -> ProjectMapper.toResponse(e)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to get Project List");
        }
    }

    @Override
    public List<ProjectResponseDto> getAllProjects() {

        try {
            return this.projectRepository.getAllProjects().stream().map(p -> ProjectMapper.toResponse(p)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to get Project List");
        }
    }

    @Override
    public ProjectResponseDto assignEmployeeToProject(long projectId, long employeeId) {

        validateUsersAndProject(employeeId, projectId);

        if (this.projectRepository.employeeExistInProject(projectId, employeeId))
            throw new DatabaseOperationException(" Employee is already mapped with project");
        try {
            Project project = this.projectRepository.assignEmployeeToProject(projectId, employeeId);
            return ProjectMapper.toResponse(project);

        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to assign employee to project");
        }
    }

    @Override
    public ProjectResponseDto removeEmployeeFromProject(long projectId, long employeeId) {

        validateUsersAndProject(employeeId, projectId);

        if (!this.projectRepository.employeeExistInProject(projectId, employeeId))
            throw new DatabaseOperationException("No Such user is assigned in project");

        try {
            return ProjectMapper.toResponse(this.projectRepository.removeEmployeeFromProject(projectId, employeeId));
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to  remove employee from project ");
        }

    }

    @Override
    public void deleteProject(long projectId) {
        if (!this.projectRepository.existById(projectId))
            throw new ResourceNotFoundException("Project", "projectId", projectId);
        this.projectRepository.deleteProject(projectId);
    }

    private void validateUsersAndProject(long employeeId, long projectId) {
        if (!this.employeeRepository.existById(employeeId))
            throw new ResourceNotFoundException("Employee", "employeeId", employeeId);

        if (!this.projectRepository.existById(projectId))
            throw new ResourceNotFoundException("Project", "projectId", projectId);

    }

}
