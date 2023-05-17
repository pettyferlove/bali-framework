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

/**
 * <p>
 * 终端信息
 * </p>
 *
 * @author Petty
 * @since 2021-01-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("uc_auth_client_details")
@Schema(name="AuthClientDetails对象", description="终端信息")
public class AuthClientDetails extends BaseEntity<AuthClientDetails> {

    private static final long serialVersionUID = 1L;

    @Schema(name = "应用名称名称")
    private String applicationName;

    @Schema(name = "描述信息")
    private String description;

    @Schema(name = "客户端ID")
    private String clientId;

    @Schema(name = "客户端资源集")
    private String resourceIds;

    @Schema(name = "客户端密钥")
    private String clientSecret;

    @Schema(name = "客户端密钥原文")
    private String clientSecretOriginal;

    @Schema(name = "授权模式")
    private String authorizedGrantTypes;

    @Schema(name = "Web Client回调URL")
    private String webServerRedirectUri;

    @Schema(name = "权限信息")
    private String authorities;

    @Schema(name = "token有效时间，单位毫秒")
    private Integer accessTokenValidity;

    @Schema(name = "刷新token有效时间，单位毫秒")
    private Integer refreshTokenValidity;

    @Schema(name = "附加信息")
    private String additionalInformation;

    @Schema(name = "租户ID")
    private String tenantId;

}
