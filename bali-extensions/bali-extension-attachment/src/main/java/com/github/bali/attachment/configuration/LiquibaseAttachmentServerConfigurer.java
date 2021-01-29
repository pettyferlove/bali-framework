package com.github.bali.attachment.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import javax.sql.DataSource;

/**
 * @author Petty
 */
@Configuration
public class LiquibaseAttachmentServerConfigurer {

    @Bean
    public SpringLiquibase attachmentServerLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setResourceLoader(new DefaultResourceLoader());
        liquibase.setDatabaseChangeLogLockTable("database_changelog_lock");
        liquibase.setDatabaseChangeLogTable("database_changelog");
        liquibase.setChangeLog("classpath:db/attachment/db.changelog-master.yml");
        liquibase.setShouldRun(true);
        return liquibase;
    }

}
