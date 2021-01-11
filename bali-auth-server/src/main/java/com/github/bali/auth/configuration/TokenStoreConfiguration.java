package com.github.bali.auth.configuration;

import com.github.bali.auth.provider.token.FastJsonRedisTokenStoreSerializationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author Petty
 */
@Slf4j
@Configuration
public class TokenStoreConfiguration {

    @Bean("tokenStore")
    @Primary
    @ConditionalOnClass(RedisConnectionFactory.class)
    public TokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setSerializationStrategy(new FastJsonRedisTokenStoreSerializationStrategy());
        redisTokenStore.setPrefix("security:oauth2:");
        return redisTokenStore;
    }

    @Bean("tokenStore")
    @ConditionalOnMissingClass("org.springframework.data.redis.connection.RedisConnectionFactory")
    public TokenStore inMemoryTokenStore() {
        return new InMemoryTokenStore();
    }

}
