//package com.bikash.person.repositories;
//
//import com.bikash.person.models.Post;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class PostRepositoryTest {
//
//    @Autowired
//    private  PostRepository postRepository;
//
//    @Test
//    void savePostWithoutImage() {
//
//        Post post = new Post();
//        post.setContent("Hi this is first Post ");
//        post.setCreatedAt(LocalDateTime.now());
//        this.postRepository.createPost(post, 2l);
//
//
//    }
//
//    @Test
//    void savePostWithImage() {
//
//
//        Post post = new Post();
//        post.setContent("Hi this is a post with images ");
//        post.setCreatedAt(LocalDateTime.now());
//
//        List<String> images = new ArrayList<>();
//        images.add("www.original.png");
//        images.add("www.nextImage.png");
//        post.setImageUrls(images);
//
//        this.postRepository.savePost(post, 2l);
//
//    }
//
//    @Test
//    void getPostsByEmployeeId() {
//    }
//
//    @Test
//    void getAllPost() {
//        List<Post> allPost = this.postRepository.getAllPost();
//    }
//
//    @Test
//    void getPostById() {
//    }
//}