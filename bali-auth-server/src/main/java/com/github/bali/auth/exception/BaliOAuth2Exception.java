package com.github.bali.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义OAuth2异常
 * @author Petty
 */
@SuppressWarnings("ALL")
@JsonSerialize(using = BaliOAuth2ExceptionJacksonSerializer.class)
public class BaliOAuth2Exception extends OAuth2Exception {
    public BaliOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public BaliOAuth2Exception(String msg) {
        super(msg);
    }
}
