package com.github.bali.core.framework.configuration;

import com.github.bali.core.framework.aspect.SubmitAgainAspect;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@ConditionalOnClass({RedisOperations.class})
@AutoConfigureBefore({RedisAutoConfiguration.class})
@EnableConfigurationProperties({RedisProperties.class})
public class SubmitAgainAutoConfiguration {

    @Bean
    public SubmitAgainAspect submitAgainAspect(RedisTemplate<Object, Object> redisTemplate) {
        return new SubmitAgainAspect(redisTemplate);
    }

}
