package com.github.bali.security.configuration;

import com.github.bali.security.annotation.EnableOAuth2ResourceServer;
import com.github.bali.security.converter.OAuth2AuthenticationConverter;
import com.github.bali.security.provider.error.OAuth2AuthExceptionEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author Pettyfer
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebServerAutoConfiguration extends WebSecurityConfigurerAdapter implements ImportAware {

    private String[] antPatters = new String[]{};

    private String[] ignores = new String[]{};



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (antPatters.length != 0) {
            http
                    // 设置匹配规则只处理/api/**下面的接口
                    .requestMatchers().mvcMatchers(antPatters)
                    .and()
                    .authorizeRequests()
                    .mvcMatchers(ignores).permitAll()
                    // 对确定的匹配规则进行验证处理，如果匹配规则未添加则只对下面的url进行验证，其他路径全部放行
                    .mvcMatchers(antPatters).authenticated();
        } else {
            http.requestMatchers().anyRequest().and().authorizeRequests()
                    .mvcMatchers(ignores).permitAll()
                    .anyRequest().authenticated();
        }

        http.csrf().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer()
                .authenticationEntryPoint(new OAuth2AuthExceptionEntryPoint())
                .jwt().jwtAuthenticationConverter(new OAuth2AuthenticationConverter());



    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableOAuth2ResourceServer.class.getName()));
        assert annotationAttributes != null;
        antPatters = (String[]) annotationAttributes.get("value");
        ignores = (String[]) annotationAttributes.get("ignores");
    }
}
