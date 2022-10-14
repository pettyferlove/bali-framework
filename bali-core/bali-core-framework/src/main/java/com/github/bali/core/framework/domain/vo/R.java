package com.github.bali.core.framework.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.core.framework.constants.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应数据包装
 *
 * @param <T>
 * @author Petty
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code = ResultCode.SUCCESS.getCode();

    private Long timestamp;

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

    public R(Throwable e, ResultCode code) {
        super();
        this.code = code.value();
        this.message = e.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public R(Throwable e, String message, ResultCode code) {
        super();
        this.code = code.value();
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }


}
