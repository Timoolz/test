package com.activedgetechnologies.test.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class MigrationConfig {


    @Autowired
    public MigrationConfig(DataSource dataSource,
                           @Value("${spring.flyway.baseline-on-migrate}") boolean baseline) {

        Flyway.configure().baselineOnMigrate(baseline).dataSource(dataSource).load().migrate();
    }

}
