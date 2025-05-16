package com.bikash.person.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private  long id ;
    private  String username ;
    private  String email ;
    private  String password;
    private  int passwordCount;
    List<Role> roles = new ArrayList<>();
}
