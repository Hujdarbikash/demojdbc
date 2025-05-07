package com.bikash.person.mappers;

import com.bikash.person.dtos.request.ProjectRequestDto;
import com.bikash.person.dtos.response.ProjectResponseDto;
import com.bikash.person.models.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {
    public  static Project toEntity(ProjectRequestDto request){

        Project project = new Project();
        project.setProjectName(request.getProjectName());
        List<Long>employeeIds = new ArrayList<>();
//        if(request.getEmployeeList()!=null)
//        {
//            employeeIds =  request.getEmployeeList().stream().map(e -> e.getEmployeeId()).collect(Collectors.toList());
//        }
        project.setEmployeeIds(employeeIds);
        return project;
    };
    public  static ProjectResponseDto toResponse(Project project)
    {

        ProjectResponseDto response = new ProjectResponseDto();
        if(project!=null)
        {
            response.setProjectId(project.getProjectId());
            response.setProjectName(project.getProjectName());
            response.setEmployeeIds(project.getEmployeeIds());
        }
        System.out.println(response);
        return response;
    }
}
