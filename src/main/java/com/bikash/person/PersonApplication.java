package com.bikash.person;
import com.bikash.person.dtos.request.RoleRequest;
import com.bikash.person.models.Role;
import com.bikash.person.models.RoleType;
import com.bikash.person.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PersonApplication  implements CommandLineRunner {


    private final RoleRepository roleRepository;

    public PersonApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PersonApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {


    }
}
