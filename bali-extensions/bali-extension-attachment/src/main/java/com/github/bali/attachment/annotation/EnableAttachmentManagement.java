package com.github.bali.attachment.annotation;

import com.github.bali.attachment.configuration.*;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;

import java.lang.annotation.*;


/**
 * @author Petty
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration({MybatisPlusConfigurer.class,
        LiquibaseAttachmentServerConfigurer.class,
        AttachmentAliyunConfigurer.class,
        AttachmentLocalConfigurer.class,
        AttachmentManagementConfigurer.class})
@ConditionalOnWebApplication
public @interface EnableAttachmentManagement {

}
