package com.github.bali.auth;

import com.github.bali.attachment.annotation.EnableAttachmentManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @author Petty
 */
@SpringBootApplication
@EnableAttachmentManagement
public class BaliAuthServerApplication {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(BaliAuthServerApplication.class, args);
    }

}
