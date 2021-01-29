package com.github.bali.attachment.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@ConfigurationProperties(prefix = "attachment.cloud.aliyun")
@SuppressWarnings("ALL")
public class AttachmentAliyunProperties {
    private String name;
    private String root;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
}
