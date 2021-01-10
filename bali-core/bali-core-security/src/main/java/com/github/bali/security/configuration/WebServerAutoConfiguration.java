package com.github.bali.security.configuration;

import com.github.bali.security.converter.OAuth2AuthenticationConverter;
import com.github.bali.security.provider.error.OAuth2AuthExceptionEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Pettyfer
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebServerAutoConfiguration extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .mvcMatcher("/messages/**")
                .authorizeRequests()
                .mvcMatchers("/messages/**").authenticated()
                .and()
                .oauth2ResourceServer()
                .authenticationEntryPoint(new OAuth2AuthExceptionEntryPoint())
                .jwt().jwtAuthenticationConverter(new OAuth2AuthenticationConverter());
        http.csrf().disable()
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable();
        http.rememberMe().disable();
    }

}
