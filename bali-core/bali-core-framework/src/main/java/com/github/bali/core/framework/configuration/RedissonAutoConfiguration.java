package com.github.bali.core.framework.configuration;


import com.github.bali.core.framework.aspect.RedissonLockAspect;
import com.github.bali.core.framework.service.RedissonLockService;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson自动配置类
 * 用于装配RedissonLockService
 * @author 刘洋
 * @version 1.0.0
 */
@Configuration
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnMissingBean(RedissonClient.class)
@AutoConfigureBefore(org.redisson.spring.starter.RedissonAutoConfiguration.class)
public class RedissonAutoConfiguration {

    @Bean
    public RedissonLockService redissonLockService(RedissonClient redissonClient) {
        return new RedissonLockService(redissonClient);
    }

    @Bean
    public RedissonLockAspect redissonLockAspect(RedissonLockService redissonLockService) {
        return new RedissonLockAspect(redissonLockService);
    }


}
