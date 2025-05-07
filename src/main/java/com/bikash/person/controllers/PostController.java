package com.bikash.person.controllers;

import com.bikash.person.dtos.request.PostRequestDto;
import com.bikash.person.dtos.response.PostResponseDto;
import com.bikash.person.globalresponse.RestResponse;
import com.bikash.person.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("v1/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/employee-id/{employeeId}")
    public ResponseEntity<?> create(@Valid  @ModelAttribute PostRequestDto requestDto, @PathVariable("employeeId") long employeeId) throws IOException {

        PostResponseDto post = this.postService.createPost(requestDto, employeeId);
        return new RestResponse<>().createdWithPayload(post, "Post Created Successfully");

    }


    @GetMapping("/employee-id/{employeeId}")
    public ResponseEntity<?> getPostByEmployeeId(@PathVariable("employeeId") long employeeId) {
        return new RestResponse<>().okWithPayload(this.postService.getALLPostByEmployeeId(employeeId), "Posts fetched Successfully");

    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostByPostId(@PathVariable("postId") long postId) {
        PostResponseDto post = this.postService.getPostByPostId(postId);
        return new RestResponse<>().okWithPayload(post, "Post fetched Successfully");

    }

    @GetMapping("")
    public ResponseEntity<?> getAllPosts() {
        List<PostResponseDto> aLlPosts = this.postService.getALlPosts();
        return new RestResponse<>().okWithPayload(aLlPosts, "Post fetched Successfully");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") long postId) {
        this.postService.deletePost(postId);
        return new RestResponse<>().okWithPayload(true, "Post deleted Successfully");
    }



    @PutMapping("/{postId}/employee-id/{employeeId}")
    public ResponseEntity<?> update( @Valid @ModelAttribute PostRequestDto requestDto,
                                     @PathVariable("postId") long postId,
                                     @PathVariable("employeeId") long employeeId)

            throws IOException {
        return new RestResponse<>().createdWithPayload(this.postService.updatePost(requestDto, postId,employeeId), "Post updated Successfully");

    }


}
