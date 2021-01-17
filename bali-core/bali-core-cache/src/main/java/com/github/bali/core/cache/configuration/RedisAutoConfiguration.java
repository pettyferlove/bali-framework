package com.github.bali.core.cache.configuration;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.github.bali.core.cache.properties.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author Pettyfer
 */
@Slf4j
@Configuration
@EnableCaching(proxyTargetClass = true)
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(value = "spring.redis.host")
public class RedisAutoConfiguration extends CachingConfigurerSupport {

    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setDefaultSerializer(valueSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericFastJsonRedisSerializer();
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(cacheProperties.getExpiration()))
                .prefixCacheNameWith(cacheProperties.getPrefix() + ":")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder(32);
            sb.append(o.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            if (objects.length > 0) {
                sb.append("#");
            }
            String sp = "";
            for (Object object : objects) {
                sb.append(sp);
                if (object == null) {
                    sb.append("NULL");
                } else {
                    sb.append(object.toString());
                }
                sp = ":";
            }
            return sb.toString();
        };
    }


}
