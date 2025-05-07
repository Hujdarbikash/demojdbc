package com.bikash.person.service.impl;

import com.bikash.person.dtos.request.PostRequestDto;
import com.bikash.person.dtos.response.PostResponseDto;
import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.exceptions.ResourceNotFoundException;
import com.bikash.person.exceptions.UnauthorizedException;
import com.bikash.person.mappers.PostMapper;
import com.bikash.person.models.Post;
import com.bikash.person.repositories.EmployeeRepository;
import com.bikash.person.repositories.PostRepository;
import com.bikash.person.service.ImageUploaderService;
import com.bikash.person.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final ImageUploaderService imageUploaderService;
    private final PostRepository postRepository;
    private final EmployeeRepository employeeRepository;

    public PostServiceImpl(ImageUploaderService imageUploaderService, PostRepository postRepository, EmployeeRepository employeeRepository) {
        this.imageUploaderService = imageUploaderService;
        this.postRepository = postRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public PostResponseDto createPost(PostRequestDto requestDto, long employeeId) {

        if (!this.employeeRepository.existById(employeeId))
            throw new ResourceNotFoundException("Employee", "employeeId", employeeId);

        try {
            List<String> imageUrls = new ArrayList<>();
            if (requestDto.getMultipartFiles() != null) {
                for (MultipartFile file : requestDto.getMultipartFiles()) {
                    String imageUrl = this.imageUploaderService.uploadImage(file);
                    imageUrls.add(imageUrl);
                }
            }
            requestDto.setImageUrls(imageUrls);
            return PostMapper.toResponse(this.postRepository.createPost(PostMapper.toEntity(requestDto), employeeId));
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to create Post");
        }
    }

    @Override
    public PostResponseDto getPostByPostId(long postId) {

        try {
            return PostMapper.toResponse(this.postRepository.getPostByPostId(postId));
        } catch (Exception e) {
          throw new ResourceNotFoundException("Post","postId",postId);
        }
    }

    @Override
    public List<PostResponseDto> getALlPosts() {
        try {
            return this.postRepository.getALlPosts().stream().map(e -> PostMapper.toResponse(e)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to retrieve posts data from database");
        }

    }

    @Override
    public List<PostResponseDto> getALLPostByEmployeeId(long employeeId) {

        if (!this.employeeRepository.existById(employeeId))
            throw new ResourceNotFoundException("Employee", "employeeId", employeeId);

        try {
            return this.postRepository.getALLPostByEmployeeId(employeeId).stream().map(e -> PostMapper.toResponse(e)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to retrieve posts data from database");
        }
    }

    @Override
    public void deletePost(long postId) {

        if (!this.postRepository.existById(postId))
            throw new ResourceNotFoundException("Post", "postId", postId);
        try {
            this.postRepository.deletePost(postId);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to retrieve posts data from database");
        }
    }

    @Override
    public PostResponseDto updatePost(PostRequestDto requestDto, long postId, long employeeId) {
        if (!this.postRepository.existById(postId))
            throw new ResourceNotFoundException("Post", "postId", postId);
        Post postByPostId = this.postRepository.getPostByPostId(postId);
        if(postByPostId.getEmployeeId() != employeeId)
            throw new UnauthorizedException("you are not allowed to update this post");


        try {
            List<String> imageUrls = new ArrayList<>();
            if (requestDto.getMultipartFiles() != null) {
                for (MultipartFile file : requestDto.getMultipartFiles()) {
                    String imageUrl = this.imageUploaderService.uploadImage(file);
                    imageUrls.add(imageUrl);
                }
            }
            requestDto.setImageUrls(imageUrls);
            return PostMapper.toResponse(this.postRepository.updatePost(PostMapper.toEntity(requestDto), postId));
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update post ");
        }

    }
}
