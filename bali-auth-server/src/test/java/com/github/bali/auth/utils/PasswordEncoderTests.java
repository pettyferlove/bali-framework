package com.github.bali.auth.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTests {

    @Test
    void contextLoads() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        boolean matches = passwordEncoder.matches("123456", "{bcrypt}$2a$10$X4VjCMZ/RgW.FU5R2UQ3.enkA6NBueXNSOmU1ceqADI5sVwbMZrwm");
        System.out.println(matches);
    }

}
