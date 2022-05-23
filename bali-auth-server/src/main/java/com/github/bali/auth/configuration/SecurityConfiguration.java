/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.bali.auth.configuration;

import com.github.bali.auth.provider.authentication.*;
import com.github.bali.auth.provider.filter.ImageCodeValidateFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsUtils;

/**
 * @author Petty
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PersistentTokenRepository tokenRepository;

    private final ImageCodeValidateFilter imageCodeValidateFilter;

    private final DefaultAuthenticationProvider defaultAuthenticationProvider;

    private final WeChatAuthenticationProvider weChatAuthenticationProvider;

    private final WeChatOpenIdAuthenticationProvider weChatOpenIdAuthenticationProvider;

    private final WeChatUnionIdAuthenticationProvider weChatUnionIdAuthenticationProvider;

    private final WriteOffAuthenticationProvider writeOffAuthenticationProvider;


    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/actuator", "/actuator/**", "/captcha/**", "/webjars/**", "/images/**", "/static/**", "/favicon**", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .cors()
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll()
                //跨域配置结束
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authorize")
                .and()
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login", "/.well-known/jwks.json").permitAll()
                .anyRequest().authenticated()
                .and()
                .rememberMe()
                .tokenRepository(tokenRepository)
                .tokenValiditySeconds(604800)
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(defaultAuthenticationProvider);
        builder.authenticationProvider(weChatAuthenticationProvider);
        builder.authenticationProvider(weChatOpenIdAuthenticationProvider);
        builder.authenticationProvider(weChatUnionIdAuthenticationProvider);
        builder.authenticationProvider(writeOffAuthenticationProvider);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}