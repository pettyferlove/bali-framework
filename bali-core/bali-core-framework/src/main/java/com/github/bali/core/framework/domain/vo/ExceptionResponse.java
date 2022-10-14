package com.github.bali.core.framework.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.core.framework.constants.ResultCode;
import lombok.Data;

/**
 * @author Pettyfer
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExceptionResponse implements IVO {

    private Integer code;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 消息返回时间戳
     */
    private Long timestamp;

    public ExceptionResponse(String message) {
        super();
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResponse(String message, ResultCode code) {
        super();
        this.message = message;
        this.code = code.getValue();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResponse(Throwable e) {
        super();
        this.message = e.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResponse(Throwable e, ResultCode code) {
        super();
        this.code = code.getValue();
        this.message = e.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResponse(Throwable e, String message, ResultCode code) {
        super();
        this.code = code.getValue();
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

}
