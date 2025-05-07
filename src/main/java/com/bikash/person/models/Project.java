package com.bikash.person.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Project {

    private  Long projectId;
    private  String projectName;
    private  List<Long> employeeIds;

}
