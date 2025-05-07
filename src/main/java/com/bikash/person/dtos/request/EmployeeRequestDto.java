package com.bikash.person.dtos.request;

import com.bikash.person.models.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeRequestDto {



    @NotBlank(message = "Name cannot be blank")
    private  String name;

    @Email
    @NotBlank
    private  String email;

    @NotNull(message = "Department cannot be null")
    private Department department;

}
