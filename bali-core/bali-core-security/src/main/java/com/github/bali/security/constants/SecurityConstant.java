package com.github.bali.security.constants;

/**
 * @author Petty
 */
public interface SecurityConstant {

    /**
     * 角色前缀
     */
    String ROLE_PREFIX = "ROLE_";

    String GRANT_TYPE = "grant_type";

    /**
     * 基础用户角色
     */
    String BASE_ROLE = "USER";

    /**
     * 系统超级管理员角色
     */
    String SUPER_ADMIN_ROLE = "SUPER_ADMIN";

    /**
     * 系统超级管理员角色ID
     */
    String SUPER_ADMIN_ROLE_ID = "0000000000000000001";

    /**
     * 租户管理员角色
     */
    String TENANT_ADMIN_ROLE = "TENANT_ADMIN";

    /**
     * 租户管理员角色
     */
    String TENANT_ADMIN_ROLE_ID = "0000000000000000002";

    /**
     * 用户无效
     */
    int STATUS_INVALID = 0;
    /**
     * 用户正常
     */
    int STATUS_NORMAL = 1;
    /**
     * 用户锁定
     */
    int STATUS_LOCK = 9;

    /**
     * 用户凭证无效
     */
    int STATUS_CREDENTIALS_EXPIRED = 8;

}
