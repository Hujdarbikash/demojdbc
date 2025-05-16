package com.bikash.person.repositories;

import com.bikash.person.dtos.request.RoleRequest;
import com.bikash.person.mappers.RoleRowMapper;
import com.bikash.person.models.Role;
import com.bikash.person.repositories.customRepositories.RoleCustomRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class RoleRepository implements RoleCustomRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Role createRole(RoleRequest roleRequest) {
        String sql = "insert into role (name) values (?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update((connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"role_id"});
            statement.setString(1, roleRequest.getName().toString());
            return statement;
        }), keyHolder);

        long role_id = keyHolder.getKey().longValue();

        Role role = new Role();
        role.setId(role_id);
        role.setName(roleRequest.getName());
        return role;
    }

    @Override
    public boolean existByRoleName(String roleName) {
        String sql = "select count(*) from role where name = ?";
        Integer result = this.jdbcTemplate.queryForObject(sql, Integer.class, roleName);
        return  result>0;

    }

    @Override
    public Role geRoleById(long id) {
        String sql= "select * from role where role_id=?";
        Role role = this.jdbcTemplate.queryForObject(sql, new RoleRowMapper(), id);
        return  role;
    }

    @Override
    public List<Role> getAllRoles() {
        String sql = "select * from role";
        List<Role> roles = this.jdbcTemplate.query(sql, new RoleRowMapper());
        return  roles;
    }

    @Override
    public List<Role> getALlRolesByUserId(long userId) {


        String sql = "select r.role_id,r.name "+
                "from users u "+
                "join users_role  ur on u.user_id = ur.user_id " +
                "join role r on ur.role_id =r.role_id "+
                "where u.user_id = ?";

        List<Role> roles = this.jdbcTemplate.query(sql, new RoleRowMapper(), userId);
        return  roles;

    }


}

