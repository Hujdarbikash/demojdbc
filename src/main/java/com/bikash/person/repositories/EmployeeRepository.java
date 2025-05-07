package com.bikash.person.repositories;

import com.bikash.person.models.Address;
import com.bikash.person.models.Department;
import com.bikash.person.models.Employee;
import com.bikash.person.repositories.customRepositories.EmployeeCustomRepository;
import com.bikash.person.rowmappers.DepartmentRowMapper;
import com.bikash.person.rowmappers.EmployeeRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository implements EmployeeCustomRepository {

    private final JdbcTemplate jdbcTemplate;
    private  final  PostRepository postRepository;
    private  final  ProjectRepository projectRepository;

    public EmployeeRepository(JdbcTemplate jdbcTemplate, PostRepository postRepository, ProjectRepository projectRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.postRepository = postRepository;
        this.projectRepository = projectRepository;
    }
    @Override
    public Employee createEmployee(Employee employee, long departmentId) {
        String sql = "insert into employee(name,email,department_id) values (?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update((connection) -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"employee_id"});
            statement.setString(1, employee.getName());
            statement.setString(2,employee.getEmail());
            statement.setLong(3, departmentId);

            return statement;
        }, keyHolder);

        long savedEmployeeId = keyHolder.getKey().longValue();
        Employee savedEmployee = getEmployeeById(savedEmployeeId);
        return savedEmployee;

    }

    public Employee getEmployeeById(long employeeId) {
        String sql = "select * from employee where  employee_id = ?";
        Employee employees= jdbcTemplate.queryForObject(sql, new Object[]{employeeId}, new EmployeeRowMapper());
        return setEmployeeALlField(employees);

    }

    @Override
    public List<Employee> getAllEmployee() {
        String sql = "select * from employee";
        List<Employee> employeeResult = this.jdbcTemplate.query(sql, new EmployeeRowMapper());
        List<Employee> employeesList = employeeResult.stream().map(employee -> setEmployeeALlField(employee)).collect(Collectors.toList());
        return  employeesList;
    }


    @Override
    public Employee updateEmployee(Employee employee, long employeeId) {
        return null;
    }

    @Override
    public void deleteEmployee(long employeeId) {

        String sql = "delete from employee where employee_id = ?";
        this.jdbcTemplate.update(sql, employeeId);

    }

    @Override
    public boolean existById(Long employeeId) {

        String sql = "select count(*) from employee where employee_id = ?";
        Integer exist = this.jdbcTemplate.queryForObject(sql, Integer.class, employeeId);
        if (exist != 1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean existByEmail(String email) {

        String sql = "select count(*) from employee where email = ?";
        Integer count = this.jdbcTemplate.queryForObject(sql,new Object[]{email},Integer.class);
        return count>0;
    }


    private List<Address> getEmployeeAddresses(long employeeId) {
        List<Address> addresses = jdbcTemplate.query(
                "SELECT * FROM address WHERE employee_id = ?",
                new Object[]{employeeId},
                (r, i) -> {
                    Address a = new Address();
                    a.setAddressId(r.getLong("address_id"));
                    a.setCity(r.getString("city"));
                    a.setStreet(r.getString("street"));
                    a.setEmployeeId(r.getLong("employee_id"));
                    return a;
                }
        );
        return addresses;
    }

    private Department getEmployeeDepartment(long employeeId) {
        String sql = "select d.department_id,d.department_name from department d " +
                " join employee e on e.department_id = d.department_id" +
                " where e.employee_id = ?";
        Department department= this.jdbcTemplate.queryForObject(sql, new Object[]{employeeId}, new DepartmentRowMapper());
        return department;
    }

    private  Employee setEmployeeALlField(Employee request)
    {
        Employee employee = new Employee() ;
        employee.setEmployeeId(request.getEmployeeId());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(getEmployeeDepartment(request.getEmployeeId()));
        employee.setAddresses(getEmployeeAddresses(request.getEmployeeId()));
        employee.setPosts(this.postRepository.getALLPostByEmployeeId(request.getEmployeeId()));
        employee.setProjects(this.projectRepository.getProjectByEmployeeId(request.getEmployeeId()));
        return  employee;
    }

}
