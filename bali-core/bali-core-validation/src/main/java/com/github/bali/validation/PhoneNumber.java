package com.github.bali.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * 验证是否为手机号码
 *
 * @author Petty
 */
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "无效的手机号码";

    Class[] groups() default {};

    Class[] payload() default {};
}
