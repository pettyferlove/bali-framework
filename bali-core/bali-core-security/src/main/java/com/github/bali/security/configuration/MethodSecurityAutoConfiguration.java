package com.github.bali.security.configuration;

import com.github.bali.security.access.BaliPermissionEvaluator;
import com.github.bali.security.access.handler.BaliMethodSecurityExpressionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * @author Pettyfer
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MethodSecurityAutoConfiguration extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        BaliMethodSecurityExpressionHandler expressionHandler =
                new BaliMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new BaliPermissionEvaluator());
        return expressionHandler;
    }

}
