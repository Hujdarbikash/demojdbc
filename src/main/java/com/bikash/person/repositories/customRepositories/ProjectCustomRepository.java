package com.bikash.person.repositories.customRepositories;

import com.bikash.person.models.Project;

import java.util.List;

public interface ProjectCustomRepository {

    public Project createProject(Project project);

    public Project getProjectById(long projectId);

    public  List<Project> getProjectByEmployeeId(long employeeId);

    public List<Project> getAllProjects();

    public Project assignEmployeeToProject(long projectId, long employeeId);

    public Project removeEmployeeFromProject(long projectId, long employeeId);

    public void deleteProject(long projectId);

    boolean existById(long projectId);

    boolean employeeExistInProject(long projectId,long employeeId);

    boolean existByName(String  projectName);


}
