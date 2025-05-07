//package com.bikash.person.repositories;
//
//import com.bikash.person.dtos.request.EmployeeRequestDto;
//import com.bikash.person.models.Employee;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class EmployeeRepositoryTest {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
////    @Test
////    @DisplayName("Saving Employee Test")
////    public  void  saveEmployeeTest()
////    {
////        EmployeeRequestDto request = new EmployeeRequestDto();
////        request.setName("Test");
////        request.setDepartment(1l);
////        Employee employee = new Employee();
////        employee.setName("HI");
////        this.employeeRepository.createEmployee(employee,request.getDepartmentId());
////    }
//
//    @Test
//    @DisplayName("EmployeeByEmployeeId")
//    public  void  findByEmployeeId()
//    {
//        Employee employee = this.employeeRepository.getEmployeeById(11l);
//        System.out.println(employee);
//    }
//
//
//}