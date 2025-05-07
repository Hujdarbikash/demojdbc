package com.bikash.person.repositories.customRepositories;

import com.bikash.person.models.Employee;

import java.util.List;

public interface EmployeeCustomRepository {

    public Employee createEmployee(Employee employee,long departmentId);

    public  Employee getEmployeeById(long employeeId);

    public List<Employee> getAllEmployee();

    public  Employee updateEmployee(Employee employee,long employeeId);

    public  void deleteEmployee(long employeeId);

     boolean existById(Long employeeId);

     boolean existByEmail(String email);


}
