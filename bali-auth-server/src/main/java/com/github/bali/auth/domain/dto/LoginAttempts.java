package com.github.bali.auth.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Pettyfer
 */
@Data
@Builder
public class LoginAttempts {

    private String id;

    /**
     * 登录用户名
     */
    private String username;

    private Integer loginFailNum;

    private LocalDateTime lastLoginFailTime;

}