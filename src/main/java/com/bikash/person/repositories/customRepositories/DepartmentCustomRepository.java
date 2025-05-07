package com.bikash.person.repositories.customRepositories;

import com.bikash.person.models.Department;

import java.util.List;

public interface DepartmentCustomRepository {

    public  Department createDepartment (Department department);

    public  Department getDepartmentById(long departmentId);

    List<Department> getAllDepartment();
    void deleteDepartment(long departmentId);

    Department updateDepartment(Department department,long departmentId);

    boolean existById(long departmentId);

    boolean existByName(String name);

}
