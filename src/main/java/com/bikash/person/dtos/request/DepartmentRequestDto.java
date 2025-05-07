package com.bikash.person.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentRequestDto {

    @NotNull()
    @Size(min = 2,message = "department name should be minimum of length 2")
    private  String departmentName;
}
