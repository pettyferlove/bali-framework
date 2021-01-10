package com.github.bali.example;

import com.github.bali.security.annotation.EnableOAuth2ResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Pettyfer
 */
@SpringBootApplication
@EnableOAuth2ResourceServer
public class BaliResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaliResourceServerApplication.class, args);
    }

}
