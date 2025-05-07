package com.bikash.person.dtos.response;

import com.bikash.person.models.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDto {
    private Long projectId ;
    private  String projectName;

    private List<Long> employeeIds;
}
