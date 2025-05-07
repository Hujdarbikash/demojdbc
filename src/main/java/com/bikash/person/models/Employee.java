package com.bikash.person.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    private long employeeId;
    private  String email;
    private String name;
    private List<Address> addresses;
    private Department department;
    private List<Post> posts;
    private List<Project> projects;

}
