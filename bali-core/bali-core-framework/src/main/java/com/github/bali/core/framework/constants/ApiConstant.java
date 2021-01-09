package com.github.bali.core.framework.constants;

/**
 * @author Petty
 */
public interface ApiConstant {

    /**
     * Api统一前缀
     */
    String API_PREFIX = "/api";

    String API_MATCH_PREFIX = "/api/**";

    /**
     * Api V1统一前缀
     */
    String API_V1_PREFIX = API_PREFIX + "/v1";

    String API_V2_PREFIX = API_PREFIX + "/v2";

    String API_V3_PREFIX = API_PREFIX + "/v3";

    String API_V4_PREFIX = API_PREFIX + "/v4";

    String API_V5_PREFIX = API_PREFIX + "/v5";

    /**
     * WebSocket 处理成功
     */
    int WS_SUCCESS = 200001;

    /**
     * WebSocket 订阅成功
     */
    int WS_WELCOME = 200000;
}
