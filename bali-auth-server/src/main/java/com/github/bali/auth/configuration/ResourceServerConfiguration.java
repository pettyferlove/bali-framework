package com.github.bali.auth.configuration;

import com.github.bali.security.provider.error.OAuth2AuthExceptionEntryPoint;
import com.github.bali.core.framework.constants.ApiConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author Petty
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        OAuth2AuthExceptionEntryPoint entryPoint = new OAuth2AuthExceptionEntryPoint();
        resources.authenticationEntryPoint(entryPoint);
        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().frameOptions().disable();
        httpSecurity
                // 设置匹配规则只处理/api/**下面的接口
                .requestMatchers().antMatchers(ApiConstant.API_MATCH_PREFIX, "/resource/**")
                .and()
                .authorizeRequests()
                // 对确定的匹配规则进行验证处理，如果匹配规则未添加则只对下面的url进行验证，其他路径全部放行
                .antMatchers(ApiConstant.API_MATCH_PREFIX, "/resource/**").authenticated();

    }


}
