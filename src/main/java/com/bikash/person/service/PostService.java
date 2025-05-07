package com.bikash.person.service;

import com.bikash.person.dtos.request.PostRequestDto;
import com.bikash.person.dtos.response.PostResponseDto;

import java.util.List;

public interface PostService {

    public PostResponseDto createPost(PostRequestDto requestDto,long employeeId);

    public  PostResponseDto getPostByPostId(long postId);

    public List<PostResponseDto> getALlPosts();

    public  List<PostResponseDto> getALLPostByEmployeeId(long employeeId);

    public  void deletePost(long postId);

    public  PostResponseDto updatePost(PostRequestDto requestDto, long postId,long employeeId);





}
