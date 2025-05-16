package com.bikash.person.configs;

import com.bikash.person.dtos.request.RoleRequest;
import com.bikash.person.dtos.request.UserRequest;
import com.bikash.person.models.Role;
import com.bikash.person.models.RoleType;
import com.bikash.person.models.User;
import com.bikash.person.repositories.RoleRepository;
import com.bikash.person.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
public class SqlScriptExecutor {
    private final DataSource dataSource;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;


    public SqlScriptExecutor(DataSource dataSource, RoleRepository roleRepository, UserRepository userRepository) {
        this.dataSource = dataSource;

        this.roleRepository = roleRepository;

        this.userRepository = userRepository;
    }

    @PostConstruct
    public void executeSqlScript() {
        try {
            ResourceDatabasePopulator populate = new ResourceDatabasePopulator();
            populate.addScript(new ClassPathResource("generalPatch/initial.sql"));
            populate.setSeparator("GO");
            DatabasePopulatorUtils.execute(populate, dataSource);
            System.out.println("******************* SQL script executed successfully ****************");
        } catch (Exception e) {
            System.err.println("***************** Failed to execute SQL script: **********************" + e.getMessage());
            e.printStackTrace();
        }
        crateUserWithRole();
    }

    private void crateUserWithRole() {


        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("admin@gmail.com");
        userRequest.setUsername("admin");
        userRequest.setPassword("admin1234");

        if (this.userRepository.findByUsername(userRequest.getEmail()).isPresent()) {
            log.info("Admin is  already created skipping the creation part");
        } else {


            RoleRequest request = new RoleRequest();
            request.setName(RoleType.ROLE_ADMIN);
            Role role = roleRepository.createRole(request);
            System.out.println(role);
            User user = this.userRepository.createUser(userRequest, role.getId());
            System.out.println(user);
        }


    }


}

