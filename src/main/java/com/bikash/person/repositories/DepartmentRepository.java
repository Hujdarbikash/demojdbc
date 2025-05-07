package com.bikash.person.repositories;

import com.bikash.person.models.Department;
import com.bikash.person.repositories.customRepositories.DepartmentCustomRepository;
import com.bikash.person.rowmappers.DepartmentRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class DepartmentRepository implements DepartmentCustomRepository {

    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Department createDepartment(Department department) {
        String sql = "insert into department(department_name) values (?)";
        // in ordered to capture a generated auto incremented id we have to  use Key Holder
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"department_id"});
            preparedStatement.setString(1, department.getDepartmentName());
            return preparedStatement;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        department.setDepartmentId(generatedId);
        return department;
    }

    @Override
    public Department getDepartmentById(long departmentId) {
        String sql = "select * from department where department_id = ?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{departmentId}, new DepartmentRowMapper());
    }

    @Override
    public List<Department> getAllDepartment() {
        String sql = "select * from department";
        return this.jdbcTemplate.query(sql, new DepartmentRowMapper());
    }

    @Override
    public void deleteDepartment(long departmentId) {
        String sql = "delete from department where department_id=?";
        this.jdbcTemplate.update(sql, departmentId);
    }

    @Override
    public Department updateDepartment(Department department, long departmentId) {

        String sql = "update department set department_name = ? where department_id= ?";
        int update = this.jdbcTemplate.update(sql, department.getDepartmentName(), departmentId);
        return getDepartmentById(departmentId);
    }

    @Override
    public boolean existById(long departmentId) {

        String sql = "select count(*) from department where department_id = ?";
        int count = this.jdbcTemplate.queryForObject(sql, new Object[]{departmentId}, Integer.class);
        return count > 0;
    }


    public  boolean existByName(String name)
    {
        String sql = "select count(*) from department where department_name = ?";
        int count = this.jdbcTemplate.queryForObject(sql,new Object[]{name}, Integer.class);
        return  count>0;
    }
}
