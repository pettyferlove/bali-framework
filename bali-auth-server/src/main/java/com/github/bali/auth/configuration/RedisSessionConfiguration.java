package com.github.bali.auth.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author Petty
 */
@Slf4j
@Configuration
@EnableCaching(proxyTargetClass = true)
@EnableRedisHttpSession
@ConditionalOnWebApplication
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(value = "spring.redis.host")
public class RedisSessionConfiguration {

    @Bean
    @Primary
    public RedisIndexedSessionRepository sessionRepository(RedisTemplate<Object, Object> redisTemplate) {
        RedisIndexedSessionRepository sessionRepository = new RedisIndexedSessionRepository(redisTemplate);
        sessionRepository.setRedisKeyNamespace("security:session");
        return sessionRepository;
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        //cookie名字
        defaultCookieSerializer.setCookieName("session-id");
        //不同子域时设置
        //defaultCookieSerializer.setDomainName("xxx.com");
        //设置各web应用返回的cookiePath一致
        defaultCookieSerializer.setCookiePath("/");
        return defaultCookieSerializer;
    }

}
