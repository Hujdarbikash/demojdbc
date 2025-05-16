package com.bikash.person.repositories.customRepositories;

import com.bikash.person.dtos.request.RoleRequest;
import com.bikash.person.models.Role;

public interface RoleCustomRepository {
    Role createRole(RoleRequest roleRequest);

    boolean existByRoleName(String roleName);

}
