package com.github.bali.attachment.configuration;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.github.bali.attachment.api.AttachmentApi;
import com.github.bali.attachment.factory.impl.AttachmentServiceFactoryImpl;
import com.github.bali.attachment.properties.AttachmentAliyunProperties;
import com.github.bali.attachment.properties.AttachmentLocalProperties;
import com.github.bali.attachment.service.impl.AttachmentAliyunServiceImpl;
import com.github.bali.attachment.service.impl.AttachmentInfoServiceImpl;
import com.github.bali.attachment.service.impl.AttachmentOperaServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Petty
 */
@Configuration
@Import({
        AttachmentApi.class,
        AttachmentAliyunProperties.class,
        AttachmentLocalProperties.class,
        AttachmentAliyunServiceImpl.class,
        AttachmentInfoServiceImpl.class,
        AttachmentOperaServiceImpl.class,
        AttachmentServiceFactoryImpl.class
})
public class AttachmentManagementConfigurer {


    @Bean
    @ConditionalOnBean(AttachmentAliyunProperties.class)
    public OSS aliyunOSS(AttachmentAliyunProperties aliyunProperties) {
        ClientConfiguration conf = new ClientConfiguration();
        return new OSSClient(aliyunProperties.getEndpoint(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret(), conf);
    }


}
