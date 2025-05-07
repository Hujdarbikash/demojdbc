package com.bikash.person.repositories;

import com.bikash.person.models.Post;
import com.bikash.person.repositories.customRepositories.PostCustomRepository;
import com.bikash.person.rowmappers.PostRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class PostRepository implements PostCustomRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Post createPost(Post post, long employeeId) {
        String insertPost = "insert into post (content, created_at, employee_id) VALUES (?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertPost, new String[]{"post_id"});
            ps.setString(1, post.getContent());
            ps.setTimestamp(2, Timestamp.valueOf(post.getCreatedAt()));
            ps.setLong(3, employeeId);
            return ps;
        }, keyHolder);

        Long postId = keyHolder.getKey().longValue();
        post.setPostId(postId);

        // insert image urls if it have
        if (post.getImageUrls() != null) {
            for (String url : post.getImageUrls()) {
                this.jdbcTemplate.update(
                        "insert into post_images (post_id, image_url) values (?, ?)",
                        postId, url
                );
            }
        }
        return post;
    }

    @Override
    public Post getPostByPostId(long postId) {
        String sql = "select * from post where post_id = ?";
        Post post = this.jdbcTemplate.queryForObject(sql, new Object[]{postId}, new PostRowMapper());
        post.setImageUrls(getImageUrlsFromPostId(postId));
        return post != null ? post : null;
    }

    @Override
    public List<Post> getALlPosts() {
        String sql = "select * from post";
        List<Post> postResult = this.jdbcTemplate.query(sql, new PostRowMapper());
        List<Post> postLists = postResult.stream().map(post -> {
            post.setImageUrls(getImageUrlsFromPostId(post.getPostId()));
            return post;
        }).collect(Collectors.toList());
        return postLists;
    }

    @Override
    public List<Post> getALLPostByEmployeeId(long employeeId) {
        String sql = "select * from post where employee_id = ?";
        List<Post> postResult = this.jdbcTemplate.query(sql, new Object[]{employeeId}, new PostRowMapper());
        List<Post> postLists = postResult.stream().map(post -> {
            post.setImageUrls(getImageUrlsFromPostId(post.getPostId()));
            return post;
        }).collect(Collectors.toList());
        return postLists;
    }

    @Override
    public void deletePost(long postId) {
        String sql = "delete from post where post_id=?";
        int update = this.jdbcTemplate.update(sql, new Object[]{postId});
        System.out.println(update);

    }


    @Override
    public Post updatePost(Post post, long postId) {
        String deletePrevImages= "delete from post_images where post_id=?";
        this.jdbcTemplate.update(deletePrevImages,postId);

        String sql = "update post set content=? where post_id=?";
        this.jdbcTemplate.update(sql,post.getContent(),postId);
        if(!post.getImageUrls().isEmpty())
        {
            for (String url : post.getImageUrls()) {
                this.jdbcTemplate.update(
                        "insert into post_images (post_id, image_url) values (?, ?)",
                        postId, url
                );
            }
        }
        return getPostByPostId(postId);
    }

    @Override
    public boolean existById(long postId) {
        String sql = "select count(*) from post where post_id = ?";
        Integer exist = this.jdbcTemplate.queryForObject(sql, Integer.class, postId);
        if (exist != 1) {
            return false;
        }
        return true;
    }

    private List<String> getImageUrlsFromPostId(long postId) {
        List<String> imageUrls = jdbcTemplate.query(
                "SELECT image_url FROM post_images WHERE post_id = ?",
                new Object[]{postId},
                (resultSet, i) -> resultSet.getString("image_url")
        );
        return imageUrls;

    }


}
