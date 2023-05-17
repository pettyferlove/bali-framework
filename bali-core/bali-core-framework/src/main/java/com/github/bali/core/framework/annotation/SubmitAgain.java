package com.github.bali.core.framework.annotation;

import java.lang.annotation.*;

/**
 * 重复提交注解
 * @author Petty
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface SubmitAgain {
    /**
     * 重复校验的key
     * 支持el表达式，例如：id:#{#dto.title}，或者:save:#{#dto.hashCode()}
     *空的话，直接用入参的hashCode
     * @return key
     */
    String value() default "";

    /**
     * 校验时长，单位毫秒
     * 默认3s
     * @return 校验时长
     */
    long time() default 3000L;

    /**
     * 可重复次数
     * 默认0次
     * @return 可重复次数
     */
    long count() default 0L;
}
