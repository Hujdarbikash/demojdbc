package com.bikash.person.models;

import lombok.*;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Post {
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private long employeeId;
    private List<String> imageUrls;

}
