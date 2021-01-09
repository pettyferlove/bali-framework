package com.github.bali.auth;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BaliAuthServerApplicationTests {


    @Test
    void contextLoads() {
    }

    @Test
    void generateId() {
        for (int i = 0; i < 10; i++) {
            System.out.println(IdWorker.getIdStr());
            ;
        }
    }

}
