package com.github.bali.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author Petty
 */
@SpringBootApplication
public class BaliAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaliAuthServerApplication.class, args);
    }

}
