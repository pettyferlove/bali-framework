package com.github.bali.persistence.configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
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
    public MybatisPlusInterceptor tenantSqlParser() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new DefaultTenantHandler(multiTenancyProperties)));
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

}
