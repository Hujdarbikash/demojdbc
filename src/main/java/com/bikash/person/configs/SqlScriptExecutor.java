package com.bikash.person.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.sql.Statement;

@Component
public class SqlScriptExecutor {
    private final DataSource dataSource;

    public SqlScriptExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void executeSqlScript() {
        try {
            ResourceDatabasePopulator populate = new ResourceDatabasePopulator();
            populate.addScript( new ClassPathResource("generalPatch/initial.sql"));
            populate.setSeparator("GO");
            DatabasePopulatorUtils.execute(populate,dataSource);
            System.out.println("******************* SQL script executed successfully ****************");
        } catch (Exception e) {
            System.err.println("***************** Failed to execute SQL script: **********************" + e.getMessage());
            e.printStackTrace();
        }
    }
}

