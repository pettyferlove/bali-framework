package com.github.bali.core.framework.constants;


/**
 * 业务处理结果状态
 * @author Pettyfer
 */

public enum ResultCode {
    /**
     * 处理成功
     */
    SUCCESS(0, "成功"),

    INVALID_ARGUMENT_ERROR(1001, "非法的参数"),

    PARAMETER_CHECK_ERROR(1005, "非法的参数"),

    UNAUTHORIZED_ERROR(1004, "未经授权的访问"),

    INTERNAL_SERVER_ERROR(9999, "内部服务异常"),

    WEB_SOCKET_SUCCESS(200001, "WebSocket 处理成功"),

    WEB_SOCKET_WELCOME(200000, "WebSocket 订阅成功"),

    WEB_SOCKET_FAIL(200005, "WebSocket 处理失败");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public int getValue() {
        return code;
    }

    public int value() {
        return this.code;
    }


    @Override
    public String toString() {
        return message;
    }
}
