package com.github.bali.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.wildfly.common.Assert;

@SpringBootTest
@Transactional
public class AuthClientDetailsServiceTests {

    @Autowired
    private IAuthClientDetailsService authClientDetailsService;

    @Test
    @Rollback
    void contextLoads () {
        Assert.assertTrue(authClientDetailsService.list().size()==1);
        Assert.assertTrue(authClientDetailsService.delete("1347517937222574085"));
        Assert.assertTrue(authClientDetailsService.list().isEmpty());
    }

}
