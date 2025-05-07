package com.bikash.person.dtos.response;

import com.bikash.person.models.Post;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponseDto {

    private Long employeeId;
    private String name;

    private  String email;
    private DepartmentResponseDto department;
    private List<AddressResponseDto> addresses;
    private List<ProjectResponseDto> projects;
    private  List<Post>posts;

}
