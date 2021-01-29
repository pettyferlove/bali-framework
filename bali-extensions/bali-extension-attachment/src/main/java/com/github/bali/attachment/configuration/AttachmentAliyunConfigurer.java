package com.github.bali.attachment.configuration;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.github.bali.attachment.properties.AttachmentAliyunProperties;
import com.github.bali.attachment.service.impl.AttachmentAliyunServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Pettyfer
 */
@Configuration
@ConditionalOnProperty(name = "attachment.cloud.aliyun.name")
@Import({
        AttachmentAliyunServiceImpl.class
})
public class AttachmentAliyunConfigurer {

    @Bean
    public OSS aliyunOSS(AttachmentAliyunProperties aliyunProperties) {
        ClientConfiguration conf = new ClientConfiguration();
        return new OSSClient(aliyunProperties.getEndpoint(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret(), conf);
    }

}
