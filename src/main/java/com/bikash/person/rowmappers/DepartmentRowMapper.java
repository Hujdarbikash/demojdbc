package com.bikash.person.rowmappers;

import com.bikash.person.models.Department;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentRowMapper implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        Department department = new Department();
        department.setDepartmentId(rs.getLong("department_id"));
        department.setDepartmentName(rs.getString("department_name"));
        return  department;
    }

}
