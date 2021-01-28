package com.github.bali.auth;

import com.github.bali.attachment.annotation.EnableAttachmentManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Petty
 */
@SpringBootApplication
@EnableAttachmentManagement
public class BaliAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaliAuthServerApplication.class, args);
    }

}
