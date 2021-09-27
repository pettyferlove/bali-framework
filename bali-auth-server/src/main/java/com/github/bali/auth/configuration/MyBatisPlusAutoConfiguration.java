package com.github.bali.auth.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petty
 * @date 2018/10/29
 */
@Configuration
@MapperScan("com.github.bali.auth.mapper")
public class MyBatisPlusAutoConfiguration {

}