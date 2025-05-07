package com.bikash.person.dtos.request;

import com.bikash.person.models.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRequestDto {

    @NotBlank(message = "Project name cannot be empty")
    private  String projectName;

}
