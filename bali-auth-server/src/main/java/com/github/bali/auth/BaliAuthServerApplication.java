package com.github.bali.auth;

import com.github.bali.auth.utils.SecurityUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@SpringBootApplication
@RestController
public class BaliAuthServerApplication {

    @GetMapping("user")
    public Object user() {
        Object principal = SecurityUtil.getAuthentication().getPrincipal();
        return principal;
    }

    public static void main(String[] args) {
        SpringApplication.run(BaliAuthServerApplication.class, args);
    }

}
