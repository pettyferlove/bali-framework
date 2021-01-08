package com.github.bali.auth.configuration;

import com.github.bali.auth.provider.token.FastJsonRedisTokenStoreSerializationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    @Bean
    @Primary
    @ConditionalOnBean(name = "redisConnectionFactory")
    public TokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setSerializationStrategy(new FastJsonRedisTokenStoreSerializationStrategy());
        redisTokenStore.setPrefix("oauth2:");
        return redisTokenStore;
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisConnectionFactory")
    public TokenStore inMemoryTokenStore(RedisConnectionFactory redisConnectionFactory) {
        return new InMemoryTokenStore();
    }

}
