package com.github.bali.example.configuration;

import com.github.bali.security.provider.error.OAuth2AuthExceptionEntryPoint;
import com.github.bali.security.userdetails.BaliUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

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
                .jwt().jwtAuthenticationConverter(jwt -> {
            Collection<SimpleGrantedAuthority> authorities =
                    ((Collection<String>) jwt.getClaims()
                            .get("authorities")).stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toSet());
            String username = (String) jwt.getClaims()
                    .get("username");
            BaliUserDetails userDetails = BaliUserDetails.builder().build();
            return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        });
    }

}
