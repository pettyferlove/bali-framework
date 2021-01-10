package com.github.bali.example.configuration;

import com.github.bali.example.OAuth2AuthenticationConverter;
import com.github.bali.security.provider.error.OAuth2AuthExceptionEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Pettyfer
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebServerConfiguration extends WebSecurityConfigurerAdapter {



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
    }

}
