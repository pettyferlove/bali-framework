package com.github.bali.persistence.configuration;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.github.bali.persistence.properties.MultiTenancyProperties;
import com.github.bali.persistence.provider.tenant.DefaultTenantHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petty
 */
@Slf4j
@Configuration
@ConditionalOnMissingBean(MultiTenancyProperties.class)
public class MultiTenancyAutoConfiguration {

    private final MultiTenancyProperties multiTenancyProperties;

    public MultiTenancyAutoConfiguration(MultiTenancyProperties multiTenancyProperties) {
        this.multiTenancyProperties = multiTenancyProperties;
    }

    @Bean
    public TenantSqlParser tenantSqlParser() {
        return new TenantSqlParser()
                .setTenantHandler(new DefaultTenantHandler(multiTenancyProperties) {
                });
    }

}
