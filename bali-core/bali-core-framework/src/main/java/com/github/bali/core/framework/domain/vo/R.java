package com.github.bali.core.framework.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 响应数据包装
 * @author Petty
 * @param <T>
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private HttpStatus status = HttpStatus.OK;

    private long timestamp;

    private String message;

    private T data;

    public R() {
        super();
        this.timestamp = System.currentTimeMillis();
    }

    public R(T data) {
        super();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public R(T data, String message) {
        super();
        this.data = data;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public R(Throwable e, HttpStatus status) {
        super();
        this.status = status;
        this.message = e.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public R(Throwable e, String message, HttpStatus status) {
        super();
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }


}
