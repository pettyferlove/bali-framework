package com.github.bali.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.persistence.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户注册信息
 * </p>
 *
 * @author Petty
 * @since 2021-01-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("uc_user")
@Schema(name="User对象", description="用户注册信息")
public class User extends BaseEntity<User> {

    private static final long serialVersionUID = 1L;

    @Schema(name = "用户登录ID，实际的登录名")
    private String loginId;

    @Schema(name = "账号密码")
    private String password;

    @Schema(name = "租户ID")
    private String tenantId;

    @Schema(name = "所属客户端（仅对公开注册的用户生效）")
    private String clientId;

    @Schema(name = "第三方OpenID")
    private String openId;

    @Schema(name = "第三方组织ID")
    private String unionId;

    @Schema(name = "用户所属渠道")
    private String userChannel;

    @Schema(name = "登录失败次数")
    private Integer loginFailNum;

    @Schema(name = "最后一次登录失败时间")
    private LocalDateTime lastLoginFailTime;

    @Schema(name = "是否有效 0：无效 1：有效 8：凭证无效 9：锁定")
    private Integer status;

}
