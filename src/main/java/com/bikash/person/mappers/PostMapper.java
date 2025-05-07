package com.bikash.person.mappers;

import com.bikash.person.dtos.request.PostRequestDto;
import com.bikash.person.dtos.response.PostResponseDto;
import com.bikash.person.models.Employee;
import com.bikash.person.models.Post;

import java.time.LocalDateTime;

public class PostMapper {
    public  static Post toEntity(PostRequestDto postRequestDto)
    {
        Post post = new Post();
        post.setContent(postRequestDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setImageUrls(postRequestDto.getImageUrls());
        Employee employee = new Employee();
        post.setEmployeeId(post.getEmployeeId());
        return post;
    }
    public  static PostResponseDto toResponse(Post post)
    {
        PostResponseDto  response = new PostResponseDto();
        response.setPostId(post.getPostId());
        response.setContent(post.getContent());
        response.setCreatedAt(post.getCreatedAt());
        response.setImageUrls(post.getImageUrls());
        response.setEmployeeId(post.getEmployeeId());
        return response;
    }

}
