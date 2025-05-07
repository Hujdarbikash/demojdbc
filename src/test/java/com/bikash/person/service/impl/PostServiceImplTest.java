package com.bikash.person.service.impl;

import com.bikash.person.dtos.request.PostRequestDto;
import com.bikash.person.dtos.response.PostResponseDto;
import com.bikash.person.exceptions.DatabaseOperationException;
import com.bikash.person.exceptions.ResourceNotFoundException;
import com.bikash.person.mappers.PostMapper;
import com.bikash.person.models.Post;
import com.bikash.person.repositories.EmployeeRepository;
import com.bikash.person.repositories.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.internal.MockedStaticImpl;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {


    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    private PostResponseDto response;

    private PostRequestDto request;

    private Post post;
    private MockedStatic<PostMapper> mockedPostMapper;


    private List<Post> postList = new ArrayList<>();

    @BeforeEach
    void setUp() {

        mockedPostMapper = mockStatic(PostMapper.class);

        request = new PostRequestDto();
        request.setContent("this is for testing");

        response = new PostResponseDto();
        response.setPostId(1l);
        response.setEmployeeId(1l);
        response.setContent(request.getContent());


        post = new Post();
        post.setPostId(1l);
        post.setEmployeeId(1l);
        post.setContent(response.getContent());
        postList.add(post);
        postList.add(post);


    }

    @AfterEach
    void tearDown() {
        mockedPostMapper.close();
    }

    @Test
    void createPost() {

        mockedPostMapper.when(() -> PostMapper.toEntity(any(PostRequestDto.class))).thenReturn(post);
        mockedPostMapper.when(() -> PostMapper.toResponse(any(Post.class))).thenReturn(response);

        when(this.postRepository.createPost(any(Post.class), anyLong())).thenReturn(post);

        Post result = this.postRepository.createPost(post, 1l);
        assertNotNull(result);
        assertEquals(post.getPostId(), result.getPostId());
        verify(this.postRepository).createPost(post, 1l);

    }

    @Test
    void createPostFailureTest() {

        mockedPostMapper.when(() -> PostMapper.toEntity(any(PostRequestDto.class))).thenReturn(post);
        mockedPostMapper.when(() -> PostMapper.toResponse(any(Post.class))).thenReturn(response);

        when(this.employeeRepository.existById(anyLong())).thenReturn(true);

        when(this.postRepository.createPost(any(Post.class), anyLong())).thenThrow(new RuntimeException("db Failed"));

        assertThrows(DatabaseOperationException.class, () -> this.postService.createPost(request, 1l));

    }

    @Test
    void getPostByPostId() {

        mockedPostMapper.when(() -> PostMapper.toResponse(any(Post.class))).thenReturn(response);
        when(this.postRepository.getPostByPostId(anyLong())).thenReturn(post);

        PostResponseDto result = this.postService.getPostByPostId(1l);
        assertNotNull(result);
        assertEquals(post.getPostId(), result.getPostId());
    }


    @Test
    void getPostByPostIdFailureTest() {

        mockedPostMapper.when(() -> PostMapper.toResponse(any(Post.class))).thenReturn(response);

        when(this.postRepository.getPostByPostId(anyLong())).thenThrow(new RuntimeException());

        assertThrows(ResourceNotFoundException.class, () -> this.postService.getPostByPostId(1l));
    }

    @Test
    void getALlPosts() {


        mockedPostMapper.when(() -> PostMapper.toResponse(any(Post.class))).thenReturn(response);

        when(this.postRepository.getALlPosts()).thenReturn(postList);

        List<PostResponseDto> aLlPosts = this.postService.getALlPosts();

        assertEquals(postList.size(), aLlPosts.size());

        verify(this.postRepository).getALlPosts();

    }


    @Test
    void getALlPostsFailureTest() {

        mockedPostMapper.when(() -> PostMapper.toResponse(any(Post.class))).thenReturn(response);

        when(this.postRepository.getALlPosts()).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class,()->this.postService.getALlPosts());


    }
    @Test
    void getALLPostByEmployeeId() {


        when(this.employeeRepository.existById(anyLong())).thenReturn(true);

        when(this.postRepository.getALLPostByEmployeeId(1l)).thenReturn(postList);

        mockedPostMapper.when(() -> PostMapper.toResponse(any(Post.class))).thenReturn(response);

        List<PostResponseDto> aLlPosts = this.postService.getALLPostByEmployeeId(1l);

        assertEquals(postList.size(),aLlPosts.size());

    }

    @Test
    void getALLPostByEmployeeIdNotFoundTest() {

        when(this.employeeRepository.existById(anyLong())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,()->this.postService.getALLPostByEmployeeId(1l));
    }

    @Test
    void getALLPostByEmployeeIdDbFailureExceptionTest() {


        when(this.employeeRepository.existById(anyLong())).thenReturn(true);

        when(this.postRepository.getALLPostByEmployeeId(1l)).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class,()->this.postService.getALLPostByEmployeeId(1l));


    }


    @Test
    void deletePost() {

        doNothing().when(this.postRepository).deletePost(anyLong());
        assertDoesNotThrow(()->this.postRepository.deletePost(1l));
        verify(this.postRepository).deletePost(1l);

    }

    @Test
    void deletePostPostNotFoundTest() {

        when(this.postRepository.existById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,()->this.postService.deletePost(1l));


    }

    @Test
    void deletePostDbFailedTest() {

        when(this.postRepository.existById(anyLong())).thenReturn(true);

        doThrow(new RuntimeException()).when(this.postRepository).deletePost(anyLong());

        assertThrows(DatabaseOperationException.class,()->this.postService.deletePost(1l));

        verify(this.postRepository).deletePost(1l);

    }
//
//        if (!this.postRepository.existById(postId))
//            throw new ResourceNotFoundException("Post", "postId", postId);
//    Post postByPostId = this.postRepository.getPostByPostId(postId);
//        if(postByPostId.getEmployeeId() != employeeId)
//            throw new UnauthorizedException("you are not allowed to update this post");
//

    @Test
    void updatePost() {


        mockedPostMapper.when(() -> PostMapper.toEntity(any(PostRequestDto.class))).thenReturn(post);
        mockedPostMapper.when(() -> PostMapper.toResponse(any(Post.class))).thenReturn(response);


        when(this.postRepository.updatePost(any(Post.class),anyLong())).thenReturn(post);

        Post result = this.postRepository.updatePost(post, 1l);
        assertNotNull(result);
        assertEquals(post.getPostId(), result.getPostId());

    }


    @Test
    void updatePostDatabaseExceptionFailureTest() {

        when(this.postRepository.existById(anyLong())).thenReturn(true);

        when(this.employeeRepository.existById(anyLong())).thenReturn(true);

        when(this.postRepository.getPostByPostId(anyLong())).thenReturn(post);

        when(this.postRepository.updatePost(any(Post.class), anyLong())).thenThrow(new RuntimeException());

        assertThrows(DatabaseOperationException.class,()->this.postService.updatePost(request,1l,  1l));
    }
    @Test
    void updatePostNotFoundTest() {

        when(this.postRepository.existById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,()->this.postService.updatePost(request,100l,2l));

    }

}