package com.bikash.person.repositories;

import com.bikash.person.dtos.request.UserRequest;
import com.bikash.person.mappers.UserRowMapper;
import com.bikash.person.models.User;
import com.bikash.person.repositories.customRepositories.CustomUserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements CustomUserRepository {

    private  final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Transactional
    @Override
    public User createUser(UserRequest request,long roleId) {

        String sql = "INSERT INTO users (username, email, password, passwordCount) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"user_id"});

            ps.setString(1, request.getUsername());
            ps.setString(2, request.getEmail());
            ps.setString(3, request.getPassword());
            ps.setInt(4, 1);
            return ps;
        }, keyHolder);

        long userId = keyHolder.getKey().longValue();

        // after user saved then according to role id  now i have to assign a user oles

        String assignUserRoles = "insert into users_role(user_id,role_id) values (?,?)";

        int update = this.jdbcTemplate.update(assignUserRoles, userId, roleId);


        User user = new User();
        user.setId(userId);
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;

    }
    @Override
    public Optional<User> findByUsername(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = this.jdbcTemplate.query(sql, new UserRowMapper(),email);
        return users.stream().findFirst();
    }
}
