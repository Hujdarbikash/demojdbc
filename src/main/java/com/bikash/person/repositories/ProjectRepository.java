package com.bikash.person.repositories;

import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.models.Employee;
import com.bikash.person.models.Project;
import com.bikash.person.repositories.customRepositories.ProjectCustomRepository;
import com.bikash.person.rowmappers.ProjectRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ProjectRepository implements ProjectCustomRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project createProject(Project project) {
        String projectName = project.getProjectName().toUpperCase();
        String sql = "insert into project (project_name) values (?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"project_id"});
            ps.setString(1, project.getProjectName().toUpperCase());
            return ps;
        }, keyHolder);

        Long projectId = keyHolder.getKey().longValue();
        project.setProjectId(projectId);
        project.setProjectName(projectName);
        return project;
    }

    @Override
    public Project getProjectById(long projectId) {
        String sql = "select * from project where project_id = ?";

        Project project = jdbcTemplate.queryForObject(sql, new Object[]{projectId}, new ProjectRowMapper());
        List<Long> employeesIdsList = getEmployeesOfProject(projectId);
        project.setEmployeeIds(employeesIdsList);
        return project;
    }

    @Override
    public List<Project> getProjectByEmployeeId(long employeeId) {

        String sql = "select p.project_id ,p.project_name" +
                " from project p" +
                " join employee_project ep on ep.project_id=p.project_id" +
                " where ep.employee_id=?";
        List<Project> projectResult = this.jdbcTemplate.query(sql, new ProjectRowMapper(), new Object[]{employeeId});
        List<Project> projectList = getFormattedProjectList(projectResult);
        return projectList;
    }

    public List<Project> getAllProjects() {
        String sql = "select * from project";
        List<Project> projectResult = jdbcTemplate.query(sql, new ProjectRowMapper());
        return getFormattedProjectList(projectResult);
    }

    @Override
    public Project assignEmployeeToProject(long projectId, long employeeId) {

        Project project = null;
        String sql = "insert into employee_project(project_id,employee_id) values(?,?)";
        int update = this.jdbcTemplate.update(sql, projectId, employeeId);
        if (update > 0) {
            project = getProjectById(projectId);
        } else {
            throw new DatabaseOperationException("Failed to assign employee to project ");
        }
        return project;
    }

    @Override
    public Project removeEmployeeFromProject(long projectId, long employeeId) {
        String sql = "delete from employee_project  where employee_id = ? and project_id =?";
        int delete = this.jdbcTemplate.update(sql, employeeId, projectId);
        if (delete > 0) {
            return getProjectById(projectId);
        } else {
            throw new DatabaseOperationException("No such mapping founds");
        }
    }

    @Override
    public void deleteProject(long projectId) {

        String deleteMap = "delete from employee_project  where project_id= ?";

        int update = this.jdbcTemplate.update(deleteMap, new Object[]{projectId});

        String sql = "delete from project where project_id=?";

        int test = jdbcTemplate.update(sql, new Object[]{projectId});

    }

    @Override
    public boolean existById(long projectId) {

        String sql = "select count(*) from project where project_id=?";
        Integer exist = this.jdbcTemplate.queryForObject(sql, Integer.class, projectId);
        if (exist != 1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean employeeExistInProject(long projectId, long employeeId) {
        String sql = "select count(*) from employee_project where employee_id=? and project_id=?";
        Integer exist = this.jdbcTemplate.queryForObject(sql, Integer.class, employeeId, projectId);
        if (exist != 1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean existByName(String projectName) {

        String sql = "select count (*) from project where project_name = ?";
        Integer result = this.jdbcTemplate.queryForObject(sql,new Object[]{projectName},Integer.class);
        return  result>0;

    }

    private List<Long> getEmployeesOfProject(long projectId) {

        String fetchEmployee = "select e.employee_id, e.name, e.department_id from employee e " +
                " join employee_project ep on e.employee_id = ep.employee_id " +
                " where ep.project_id = ?";

        List<Employee> employeeList = this.jdbcTemplate.query(fetchEmployee, new Object[]{projectId}
                , (eachRow, i) -> {
                    Employee e = new Employee();
                    e.setEmployeeId(eachRow.getLong("employee_id"));
                    e.setName(eachRow.getString("name"));
                    return e;
                });
        List<Long> employeeIds = employeeList.stream().map(employee -> employee.getEmployeeId()).collect(Collectors.toList());
        return employeeIds;
    }

    private List<Project> getFormattedProjectList(List<Project> projectList) {
        List<Project> formattedList = projectList.stream().map(project -> {
                    List<Long> employeesIdsList = getEmployeesOfProject(project.getProjectId());
                    project.setEmployeeIds(employeesIdsList);
                    return project;
                }
        ).collect(Collectors.toList());

        return formattedList;
    }


}
