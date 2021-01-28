package com.github.bali.attachment.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 附件配置（本地存储）
 *
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConditionalOnExpression("!'${attachment.local}'.isEmpty()")
@ConfigurationProperties(prefix = "attachment.local")
public class AttachmentLocalProperties {
    String path;
}
