package com.bikash.person.service.impl;

import com.bikash.person.dtos.request.ProjectRequestDto;
import com.bikash.person.dtos.response.ProjectResponseDto;
import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.exceptions.ResourceNotFoundException;
import com.bikash.person.mappers.ProjectMapper;
import com.bikash.person.models.Project;
import com.bikash.person.repositories.EmployeeRepository;
import com.bikash.person.repositories.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;


    private ProjectResponseDto response;

    private ProjectRequestDto request;

    private Project project;

    private MockedStatic<ProjectMapper> mockedProjectMapper;

    private List<ProjectResponseDto> projectResponseDtosList = new ArrayList<>();

    private List<Project> projectList = new ArrayList<>();

    @BeforeEach
    void setUp() {

        mockedProjectMapper = mockStatic(ProjectMapper.class);

        request = new ProjectRequestDto();
        request.setProjectName("MANUS");


        response = new ProjectResponseDto();

        List<Long> ids = new ArrayList<>();
        ids.add(1l);
        ids.add(2l);
        ids.add(3l);


        response.setProjectId(2l);
        response.setProjectName(request.getProjectName());
        response.setEmployeeIds(ids);


        project = new Project();
        project.setEmployeeIds(response.getEmployeeIds());
        project.setProjectName(response.getProjectName());
        project.setProjectId(response.getProjectId());

        projectResponseDtosList.add(response);

        projectList.add(project);
    }

    @AfterEach
    void tearDown() {

        response = null;
        request = null;
        mockedProjectMapper.close();

    }

    @Test
    void createProject() {

        mockedProjectMapper.when(() -> ProjectMapper.toEntity(any(ProjectRequestDto.class))).thenReturn(project);
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.createProject(project)).thenReturn(project);

        ProjectResponseDto result = this.projectService.createProject(request);
        assertNotNull(result);
        assertEquals(response.getProjectId(), result.getProjectId());
        assertEquals(response.getProjectName(), result.getProjectName());
        verify(this.projectRepository).createProject(project);
    }

    @Test
    void createProjectFailedExceptionTest() {

        mockedProjectMapper.when(() -> ProjectMapper.toEntity(any(ProjectRequestDto.class))).thenReturn(project);
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.createProject(project)).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class, () -> this.projectService.createProject(request));

    }


    @Test
    void getProjectById() {

        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.getProjectById(anyLong())).thenReturn(project);

        ProjectResponseDto result = this.projectService.getProjectById(2l);
        assertNotNull(result);
        assertEquals(response.getProjectId(), result.getProjectId());
        assertEquals(response.getProjectName(), result.getProjectName());


    }

    @Test
    void getProjectByIdNotFoundException() {

        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.getProjectById(anyLong())).thenThrow(new RuntimeException());

        assertThrows(ResourceNotFoundException.class, () -> this.projectService.getProjectById(1l));

    }

    @Test
    void getProjectByEmployeeId() {

        when(this.employeeRepository.existById(anyLong())).thenReturn(true);
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);
        when(this.projectRepository.getProjectByEmployeeId(anyLong())).thenReturn(projectList);

        List<ProjectResponseDto> results = this.projectService.getProjectByEmployeeId(2l);

        assertEquals(projectList.size(), results.size());
        assertEquals(projectList.get(0).getProjectId(), results.get(0).getProjectId());


    }


    @Test
    void getProjectByEmployeeIdFailureTest() {

        when(this.employeeRepository.existById(anyLong())).thenReturn(true);
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);
        when(this.projectRepository.getProjectByEmployeeId(anyLong())).thenThrow(new RuntimeException());


        assertThrows(DatabaseOperationException.class, () -> this.projectService.getProjectByEmployeeId(2l));
        verify(this.projectRepository).getProjectByEmployeeId(2l);

    }


    @Test
    void getProjectByEmployeeIdNotFound() {

        when(this.employeeRepository.existById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> this.projectService.getProjectByEmployeeId(2l));


    }

    @Test
    void getAllProjects() {
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.getAllProjects()).thenReturn(projectList);

        List<ProjectResponseDto> allProjects = this.projectService.getAllProjects();

        assertEquals(projectResponseDtosList.get(0).getProjectName(), allProjects.get(0).getProjectName());
        assertEquals(projectResponseDtosList.size(), allProjects.size());

    }

    @Test
    void getAllProjectsFailure() {
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.getAllProjects()).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class, () -> this.projectService.getAllProjects());

    }

    @Test
    void assignEmployeeToProject() {
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.existById(anyLong())).thenReturn(true);
        when(this.employeeRepository.existById(anyLong())).thenReturn(true);
        when(this.projectRepository.employeeExistInProject(anyLong(),anyLong())).thenReturn(false);

        when(this.projectRepository.assignEmployeeToProject(anyLong(),anyLong())).thenReturn(project);
        ProjectResponseDto result = this.projectService.assignEmployeeToProject(45l, 7l);
        assertEquals(project.getEmployeeIds().get(0),result.getEmployeeIds().get(0));



    }
    @Test
    @DisplayName("Employee Already Mapped ")
    void assignEmployeeToProjectAlreadyMapTest() {
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.existById(anyLong())).thenReturn(true);
        when(this.employeeRepository.existById(anyLong())).thenReturn(true);


        when(this.projectRepository.employeeExistInProject(anyLong(),anyLong())).thenReturn(true);

        DatabaseOperationException databaseOperationException = assertThrows(DatabaseOperationException.class, () -> this.projectService.assignEmployeeToProject(1l, 2l));

        assertEquals(" Employee is already mapped with project",databaseOperationException.getMessage());


    }

    @Test
    @DisplayName("Employee Already Mapped ")
    void assignEmployeeToProjectFailureTest() {
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.existById(anyLong())).thenReturn(true);
        when(this.employeeRepository.existById(anyLong())).thenReturn(true);

        when(this.projectRepository.employeeExistInProject(anyLong(),anyLong())).thenReturn(false);
        when(this.projectRepository.assignEmployeeToProject(anyLong(),anyLong())).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class,()->this.projectService.assignEmployeeToProject(1l,2l));


    }


    @Test
    void removeEmployeeFromProject() {
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.existById(anyLong())).thenReturn(true);
        when(this.employeeRepository.existById(anyLong())).thenReturn(true);
        when(this.projectRepository.employeeExistInProject(anyLong(),anyLong())).thenReturn(true);

        when(this.projectRepository.removeEmployeeFromProject(anyLong(),anyLong())).thenReturn(project);
         this.projectService.removeEmployeeFromProject(45l, 7l);

         verify(this.projectRepository).removeEmployeeFromProject(45l,7l);




    }
    @Test
    @DisplayName("Employee is not mapped")
    void removeEmployeeFromProjectAlreadyMapTest() {
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.existById(anyLong())).thenReturn(true);
        when(this.employeeRepository.existById(anyLong())).thenReturn(true);


        when(this.projectRepository.employeeExistInProject(anyLong(),anyLong())).thenReturn(false);

        DatabaseOperationException databaseOperationException = assertThrows(DatabaseOperationException.class, () -> this.projectService.removeEmployeeFromProject(1l, 2l));

        assertEquals("No Such user is assigned in project",databaseOperationException.getMessage());


    }

    @Test
    @DisplayName("Employee Failed to remove ")
    void removeEmployeeFromProjectFailureTest() {
        mockedProjectMapper.when(() -> ProjectMapper.toResponse(any(Project.class))).thenReturn(response);

        when(this.projectRepository.existById(anyLong())).thenReturn(true);
        when(this.employeeRepository.existById(anyLong())).thenReturn(true);
        when(this.projectRepository.employeeExistInProject(anyLong(),anyLong())).thenReturn(true);

        when(this.projectRepository.removeEmployeeFromProject(anyLong(),anyLong())).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class,()->this.projectService.removeEmployeeFromProject(1l,2l));


    }
    @Test
    void deleteProject() {

        when(this.projectRepository.existById(anyLong())).thenReturn(true);

        doNothing().when(this.projectRepository).deleteProject(anyLong());

        this.projectService.deleteProject(1l);
        verify(this.projectRepository).deleteProject(1l);


    }

    @Test
    void deleteProjectIdNotFoundTest() {

        when(this.projectRepository.existById(anyLong())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,()->this.projectService.deleteProject(1l));

    }

    @Test
    void deleteProjectFailureTest() {

        when(this.projectRepository.existById(anyLong())).thenReturn(true);

        doThrow(new DatabaseOperationException()).when(this.projectRepository).deleteProject(1l);


        assertThrows(DatabaseOperationException.class,()->this.projectService.deleteProject(1l));

    }
}