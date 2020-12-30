package com.github.bali.auth.configuration;

import com.github.bali.auth.provider.repository.RedisTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * @author Petty
 */

@Slf4j
@Configuration
public class RedisTokenRepositoryConfiguration {

    private final RedisTemplate<Object, Object> securityRedisTemplate;

    public RedisTokenRepositoryConfiguration(RedisTemplate<Object, Object> securityRedisTemplate) {
        this.securityRedisTemplate = securityRedisTemplate;
    }

    @Bean("tokenRepository")
    @Primary
    @ConditionalOnBean(name = "securityRedisTemplate")
    public PersistentTokenRepository redisTokenRepository() {
        return new RedisTokenRepository(securityRedisTemplate);
    }

    @Bean("tokenRepository")
    @Primary
    @ConditionalOnMissingBean(name = "securityRedisTemplate")
    public PersistentTokenRepository tokenRepository() {
        return new RedisTokenRepository(securityRedisTemplate);
    }

}
