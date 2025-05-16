package com.bikash.person.mappers;

import com.bikash.person.models.Role;
import com.bikash.person.models.RoleType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong("role_id"));
        role.setName(RoleType.valueOf(rs.getString("name")));
        return  role;
    }
}
