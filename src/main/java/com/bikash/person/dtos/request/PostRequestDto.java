package com.bikash.person.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostRequestDto {

    @NotBlank(message = "Content cannot be blank")
    private String content;

    private List<String> imageUrls;

    private List<MultipartFile> multipartFiles;

}
