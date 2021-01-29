package com.github.bali.attachment.configuration;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.github.bali.attachment.properties.AttachmentAliyunProperties;
import com.github.bali.attachment.service.IAttachmentService;
import com.github.bali.attachment.service.impl.AttachmentAliyunServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pettyfer
 */
@Configuration
@ConditionalOnProperty(name = "attachment.cloud.aliyun.name")
@SuppressWarnings("ALL")
public class AttachmentAliyunConfigurer {

    @Bean
    public IAttachmentService aliyun(AttachmentAliyunProperties aliyunProperties) {
        ClientConfiguration conf = new ClientConfiguration();
        OSSClient ossClient = new OSSClient(aliyunProperties.getEndpoint(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret(), conf);
        return new AttachmentAliyunServiceImpl(aliyunProperties, ossClient);
    }

}
