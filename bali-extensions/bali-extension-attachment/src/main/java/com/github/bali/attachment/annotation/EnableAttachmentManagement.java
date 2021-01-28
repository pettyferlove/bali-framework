package com.github.bali.attachment.annotation;

import com.github.bali.attachment.configuration.AttachmentManagementConfigurer;
import com.github.bali.attachment.configuration.MybatisPlusConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 * @author Petty
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MybatisPlusConfigurer.class,
        AttachmentManagementConfigurer.class})
@ConditionalOnWebApplication
public @interface EnableAttachmentManagement {

}
