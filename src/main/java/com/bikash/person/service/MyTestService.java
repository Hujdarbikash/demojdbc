//package com.bikash.person.service;
//
//import com.bikash.person.models.Employee;
//import com.bikash.person.repositories.EmployeeRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class MyTestService {
//
//    private final EmployeeRepository employeeRepository;
//
//    public MyTestService(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }
//
//    @Transactional
//    public void getById(){
//        Employee emp = employeeRepository.getEmployeeById(40);
//        emp.setName("Changed Name");
//    }
//}
