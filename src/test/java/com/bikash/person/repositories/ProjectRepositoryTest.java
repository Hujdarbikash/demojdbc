//package com.bikash.person.repositories;
//
//import com.bikash.person.models.Employee;
//import com.bikash.person.models.Project;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class ProjectRepositoryTest {
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//
//    @Test
//    @DisplayName("SaveProjectTest")
//    void saveProject() {
//
//        Project project = new Project();
//        project.setProjectName("OKTA");
//        Project savedProject = this.projectRepository.createProject(project);
//        System.out.println(savedProject);
//    }
//    @Test
//    @DisplayName("SaveProjectTest")
//    void saveProjectWithDefaultEmployee() {
//        Project project = new Project();
//        project.setProjectName("MANUS");
//
//        //creating a default Employees
//
//        Employee employee = new Employee();
//        employee.setEmployeeId(1);
//        List<Employee> employeeList = new ArrayList<>();
//        employeeList.add(employee);
//
//
//        project.setEmployees(employeeList);
//        Project savedProject = this.projectRepository.createProject(project);
//        System.out.println(savedProject);
//    }
//
//    @Test
//    void getAllProjects() {
//
//        List<Project> allProjects = this.projectRepository.getAllProjects();
//        System.out.println(allProjects);
//
//    }
//
//
//    @Test
//    void getProjectById() {
//        Project projectById = this.projectRepository.getProjectById(2l);
//        System.out.println(projectById);
//    }
//}