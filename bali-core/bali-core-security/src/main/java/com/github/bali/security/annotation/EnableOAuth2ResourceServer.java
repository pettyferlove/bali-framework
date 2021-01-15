package com.github.bali.security.annotation;

import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.security.configuration.MethodSecurityAutoConfiguration;
import com.github.bali.security.configuration.WebServerAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 初始化资源服务
 *
 * @author Petty
 * @version 1.0.0
 * @since 如果应用需要自己实现登录等操作，则不要使用该注解
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({WebServerAutoConfiguration.class, MethodSecurityAutoConfiguration.class})
@ConditionalOnWebApplication
public @interface EnableOAuth2ResourceServer {

    /**
     * 设置需要通过OAuth2验证的api前缀
     * 默认验证“/api”前缀
     *
     * @return String[]
     */
    String[] value() default {ApiConstant.API_PREFIX + "/**"};

    /**
     * 需要忽略的api权限，该值会在WebSecurity之前进行判断
     *
     * @return String[]
     */
    String[] ignores() default {};

}
