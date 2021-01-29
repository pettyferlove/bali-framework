package com.github.bali.attachment.configuration;

import com.github.bali.attachment.api.AttachmentApi;
import com.github.bali.attachment.factory.impl.AttachmentPretreatmentServiceFactoryImpl;
import com.github.bali.attachment.factory.impl.AttachmentServiceFactoryImpl;
import com.github.bali.attachment.service.impl.AttachmentInfoServiceImpl;
import com.github.bali.attachment.service.impl.AttachmentOperaServiceImpl;
import com.github.bali.attachment.service.impl.CompressServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Petty
 */
@Configuration
@Import({
        AttachmentApi.class,
        AttachmentInfoServiceImpl.class,
        AttachmentOperaServiceImpl.class,
        AttachmentServiceFactoryImpl.class,
        AttachmentPretreatmentServiceFactoryImpl.class,
        CompressServiceImpl.class
})
public class AttachmentManagementConfigurer {

}
