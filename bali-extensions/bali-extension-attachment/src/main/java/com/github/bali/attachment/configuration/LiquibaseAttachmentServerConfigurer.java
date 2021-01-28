package com.github.bali.attachment.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Petty
 */
@Configuration
public class LiquibaseAttachmentServerConfigurer {

    @Bean
    @ConditionalOnBean(DataSource.class)
    public SpringLiquibase attachmentServerLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setDatabaseChangeLogLockTable("database_changelog_lock");
        liquibase.setDatabaseChangeLogTable("database_changelog");
        liquibase.setChangeLog("classpath:db/db.changelog-master.yml");
        liquibase.setShouldRun(true);
        return liquibase;
    }

}
