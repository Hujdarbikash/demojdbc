package com.bikash.person.dtos.request;

import com.sun.jdi.request.StepRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private  String username ;
    private  String email ;
    private String password;
}
