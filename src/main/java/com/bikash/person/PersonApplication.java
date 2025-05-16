package com.bikash.person;
import com.bikash.person.models.User;
import com.bikash.person.repositories.RoleRepository;
import com.bikash.person.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Optional;

@EnableAsync
@SpringBootApplication
public class PersonApplication  implements CommandLineRunner {


    private final RoleRepository roleRepository;

    private  final UserRepository userRepository;

    public PersonApplication(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PersonApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {

        Optional<User> savedUser = this.userRepository.findByUsername("admin@gmail.com");
        System.out.println("**************** "+savedUser.toString());
    }
}
