package com.bikash.person.repositories;

import com.bikash.person.dtos.request.RoleRequest;
import com.bikash.person.models.Role;
import com.bikash.person.repositories.customRepositories.RoleCustomRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

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
}

