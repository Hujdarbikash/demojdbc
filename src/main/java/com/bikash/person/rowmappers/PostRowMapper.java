package com.bikash.person.rowmappers;

import com.bikash.person.models.Employee;
import com.bikash.person.models.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostRowMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setPostId(rs.getLong("post_id"));
        post.setContent(rs.getString("content"));
        post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        post.setEmployeeId(rs.getLong("employee_id"));
        return post;
    }
}
