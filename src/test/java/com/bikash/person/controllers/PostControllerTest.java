package com.bikash.person.controllers;

import com.bikash.person.dtos.request.PostRequestDto;
import com.bikash.person.dtos.response.PostResponseDto;
import com.bikash.person.service.ImageUploaderService;
import com.bikash.person.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostController.class)
class PostControllerTest {

    private PostRequestDto request;
    private PostResponseDto response;
    private List<PostResponseDto> responseList = new ArrayList<>();
    private MockMultipartFile file;

    @MockitoBean
    private PostService postService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ImageUploaderService imageUploaderService;


    @BeforeEach
    void setup() {

        request = new PostRequestDto();
        response = new PostResponseDto();

        request.setContent("This is for test case");
        file = new MockMultipartFile("multipartFiles", "test1.txt", "text/plain", "Test content 1".getBytes());

        List<MultipartFile> files = new ArrayList<>();
        files.add(file);

        request.setMultipartFiles(files);

        response.setEmployeeId(100l);
        response.setContent("This is for test case");
        response.setPostId(18l);

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("http://image.jpg");
        response.setImageUrls(imageUrls);
        responseList.add(response);


    }

    @AfterEach
    void tearUp() {
        response = null;
        request = null;

    }

    @Test
    void create() throws Exception {

        when(this.postService.createPost(any(PostRequestDto.class), any(Long.class))).thenReturn(response);

        Mockito.when(this.imageUploaderService.uploadImage(Mockito.any(MultipartFile.class)))
                .thenReturn("http://image.jpg");


        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/post/employee-id/{employeeId}", 1l)
                        .file(file)
                        .param("content", request.getContent())
                        .contentType(MediaType.MULTIPART_FORM_DATA)

                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.postId").value(18L))
                .andExpect(jsonPath("$.data.imageUrls.[0]").value(response.getImageUrls().get(0)))
                .andExpect(jsonPath("$.message").value("Post Created Successfully"));
    }

    @Test
    void getPostByEmployeeId()throws Exception {

        when(this.postService.getALLPostByEmployeeId(any(Long.class))).thenReturn(responseList);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/post/emplyee-id/{employeeId}",39l));


    }

    @Test
    void getPostByPostId()  throws  Exception{

        OngoingStubbing<PostResponseDto> postResponseDtoOngoingStubbing = when(this.postService.getPostByPostId(any(Long.class))).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/post/{postId}",1l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.postId").value(response.getPostId()));
    }

    @Test
    void getAllPosts() throws  Exception {

        when(this.postService.getALlPosts()).thenReturn(responseList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/post"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].postId").value(response.getPostId()));
    }

    @Test
    void deletePost() throws Exception {
        doNothing().when(this.postService).deletePost(1);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/post/{postId}",1))
                .andExpect(jsonPath("$.message").value("Post deleted Successfully"));
    }

    @Test
    void update() throws  Exception{

        when(this.postService.updatePost(any(PostRequestDto.class), any(Long.class),anyLong())).thenReturn(response);

        Mockito.when(this.imageUploaderService.uploadImage(Mockito.any(MultipartFile.class)))
                .thenReturn("http://image.jpg");



        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/post/{postId}", 1l)
                        .file(file)
                        .param("content", request.getContent())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.postId").value(18L))
                .andExpect(jsonPath("$.message").value("Post updated Successfully"));
    }



}