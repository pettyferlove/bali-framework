package com.github.bali.auth.configuration;

import com.github.bali.auth.provider.token.JdkSerializationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author Petty
 */
@Slf4j
@Configuration
public class TokenStoreConfiguration {

    private final RedisConnectionFactory redisConnectionFactory;

    public TokenStoreConfiguration(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setSerializationStrategy(new JdkSerializationStrategy(new JdkSerializationRedisSerializer(this.getClass().getClassLoader())));
        return redisTokenStore;
    }

}
