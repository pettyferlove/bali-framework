package com.github.bali.core.framework.annotation;

import java.lang.annotation.*;

/**
 * 分布式锁
 * @author Petty
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface RedissonLock {
    /**
     * 重复校验的key
     * 支持el表达式，例如：id:#{#dto.title}，或者:save:#{#dto.hashCode()}
     * 空的话，直接用入参的hashCode
     * @return key
     */
    String value() default "";

    /**
     * 等待时间，单位秒
     * 默认0
     * @return 校验时长
     */
    long waitSeconds() default 0L;

    /**
     * 超时时间，单位秒
     * 默认-1，则是不设置超时时间，交由看门狗控制超时时间
     * @return 可重复次数
     */
    long leaseSeconds() default -1L;
}
