package com.github.bali.auth;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BaliAuthServerApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        boolean matches = passwordEncoder.matches("123456", "{bcrypt}$2a$10$d8Xf0jV1ARsAMCgkjUie7.WRmc.HdQSV3OAO1QHSOHOGnJo2S0MiG");
        System.out.println(matches);
    }

    @Test
    void generateId() {
        for (int i = 0; i < 10; i++) {
            System.out.println(IdWorker.getIdStr());;
        }
    }

}
