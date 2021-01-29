package com.github.bali.attachment.configuration;

import com.github.bali.attachment.properties.AttachmentLocalProperties;
import com.github.bali.attachment.service.IAttachmentService;
import com.github.bali.attachment.service.impl.AttachmentLocalServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pettyfer
 */
@Configuration
@ConditionalOnProperty(name = "attachment.local.name")
public class AttachmentLocalConfigurer {

    @Bean
    public IAttachmentService local(AttachmentLocalProperties localProperties) {
        return new AttachmentLocalServiceImpl(localProperties);
    }

}
