package com.bikash.person.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private  long id;
    private  RoleType name;
    private List<User> users = new ArrayList<>();
}
