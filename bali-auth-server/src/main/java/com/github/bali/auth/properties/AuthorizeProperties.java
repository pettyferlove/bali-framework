package com.github.bali.auth.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConditionalOnExpression("!'${authorize}'.isEmpty()")
@ConfigurationProperties(prefix = "authorize")
public class AuthorizeProperties {

    /**
     * 是否开启密码加密
     */
    Boolean enablePasswordEncrypt = false;

    /**
     * 加密Key
     */
    String encryptKey;

    LoginAttempt loginAttempt;

    @Data
    public static class LoginAttempt {
        /**
         * 开启错误密码尝试登录次数限制
         */
        private Boolean enable = false;

        /**
         * 密码错误限制针对的授权模式
         */
        private List<String> granters;

        /**
         * 尝试次数
         */
        private Integer timesBeforeLock = 5;

        /**
         * 锁定时长（分钟）
         */
        private Integer lockingDuration = 30 * 60 * 1000;
    }

}
