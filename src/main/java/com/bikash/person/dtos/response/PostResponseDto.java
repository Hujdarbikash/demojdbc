package com.bikash.person.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private  long employeeId;
    private List<String> imageUrls;
}
