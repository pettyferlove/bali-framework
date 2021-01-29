package com.github.bali.attachment.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 附件配置（阿里云）
 *
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConditionalOnProperty(name = "attachment.cloud.aliyun.name")
@ConfigurationProperties(prefix = "attachment.cloud.aliyun")
public class AttachmentAliyunProperties {
    private String name;
    private String root;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
}
