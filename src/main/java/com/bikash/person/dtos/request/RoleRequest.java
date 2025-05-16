package com.bikash.person.dtos.request;

import com.bikash.person.models.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private RoleType name;
}
