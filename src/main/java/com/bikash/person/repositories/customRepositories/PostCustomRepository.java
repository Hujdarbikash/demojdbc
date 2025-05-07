package com.bikash.person.repositories.customRepositories;

import com.bikash.person.models.Post;

import java.util.List;

public interface PostCustomRepository {
    public Post createPost(Post post, long employeeId);

    public  Post getPostByPostId(long postId);

    public List<Post> getALlPosts();

    public  List<Post> getALLPostByEmployeeId(long employeeId);

    public  void deletePost(long postId);

    public  Post updatePost(Post post, long postId);

    boolean existById(long postId);


}
