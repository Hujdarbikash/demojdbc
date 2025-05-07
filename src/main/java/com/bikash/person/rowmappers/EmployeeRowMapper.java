package com.bikash.person.rowmappers;

import com.bikash.person.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        long employeeId = rs.getLong("employee_id");
        employee.setEmployeeId(employeeId);
        employee.setName(rs.getString("name"));
        employee.setEmail(rs.getString("email"));
        return employee;
    }
}
