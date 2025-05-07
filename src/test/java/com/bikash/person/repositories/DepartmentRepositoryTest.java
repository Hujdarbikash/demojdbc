//package com.bikash.person.repositories;
//
//import com.bikash.person.models.Department;
//import com.bikash.person.repositories.DepartmentRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//public class DepartmentRepositoryTest {
//
//    @Autowired
//    private DepartmentRepository departmentRepository;
//
//    @Test
//    @DisplayName("Save DepartmentTest")
//    void saveDepartmentTest() {
//        Department department = new Department();
//        department.setDepartmentName("CIVIL");
//        this.departmentRepository.createDepartment(department);
//    }
//
//    @Test
//    @DisplayName("Find By Id Test")
//    void findByIdTest() {
//        this.departmentRepository.getDepartmentById(200l);
//    }
//
//
//
//    @Test
//    @DisplayName("Get ALl Department Test")
//    void getALlDepartmentTest() {
//        List<Department> allDepartment = this.departmentRepository.getAllDepartment();
//        System.out.println(allDepartment);
//    }
//
//}
