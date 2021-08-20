package com.github.bali.persistence.configuration;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.github.bali.persistence.properties.MultiTenancyProperties;
import com.github.bali.persistence.provider.tenant.DefaultTenantHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petty
 */
@Slf4j
@Configuration
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class MultiTenancyAutoConfiguration {

    @Autowired
    private MultiTenancyProperties multiTenancyProperties;

    @Bean
    @ConditionalOnBean(MultiTenancyProperties.class)
    public TenantSqlParser tenantSqlParser() {
        return new TenantSqlParser()
                .setTenantHandler(new DefaultTenantHandler(multiTenancyProperties) {
                });
    }

}
