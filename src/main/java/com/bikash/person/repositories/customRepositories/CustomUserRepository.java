package com.bikash.person.repositories.customRepositories;

import com.bikash.person.dtos.request.UserRequest;
import com.bikash.person.models.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CustomUserRepository {

    User createUser(UserRequest request,long roleId);

    Optional<User>findByUsername(String email);
}
