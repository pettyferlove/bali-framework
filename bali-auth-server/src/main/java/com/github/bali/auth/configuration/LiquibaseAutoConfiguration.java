package com.github.bali.auth.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Liquibase配置
 * @author Petty
 */
@Configuration
public class LiquibaseAutoConfiguration {

    @Bean
    public SpringLiquibase authServerLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setDatabaseChangeLogLockTable("uc_database_changelog_lock");
        liquibase.setDatabaseChangeLogTable("uc_database_changelog");
        liquibase.setChangeLog("classpath:db/db.changelog-master.yml");
        liquibase.setShouldRun(true);
        return liquibase;
    }

}
