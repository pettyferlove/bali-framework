package com.github.bali.auth;

import com.github.bali.security.utils.SecurityUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@SpringBootApplication
public class BaliAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaliAuthServerApplication.class, args);
    }

}
