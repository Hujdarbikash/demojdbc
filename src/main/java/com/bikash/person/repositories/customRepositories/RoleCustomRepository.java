package com.bikash.person.repositories.customRepositories;

import com.bikash.person.dtos.request.RoleRequest;
import com.bikash.person.models.Role;

import java.util.List;

public interface RoleCustomRepository {
    Role createRole(RoleRequest roleRequest);

    boolean existByRoleName(String roleName);

     Role geRoleById(long id );


     List<Role>getAllRoles();


     List<Role> getALlRolesByUserId(long userId);


}
