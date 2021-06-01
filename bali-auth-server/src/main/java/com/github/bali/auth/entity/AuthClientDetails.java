package com.github.bali.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.persistence.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
@ApiModel(value="AuthClientDetails对象", description="终端信息")
public class AuthClientDetails extends BaseEntity<AuthClientDetails> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用名称名称")
    private String applicationName;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "客户端ID")
    private String clientId;

    @ApiModelProperty(value = "客户端资源集")
    private String resourceIds;

    @ApiModelProperty(value = "客户端密钥")
    private String clientSecret;

    @ApiModelProperty(value = "客户端密钥原文")
    private String clientSecretOriginal;

    @ApiModelProperty(value = "授权模式")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "Web Client回调URL")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "权限信息")
    private String authorities;

    @ApiModelProperty(value = "token有效时间，单位毫秒")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "刷新token有效时间，单位毫秒")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "附加信息")
    private String additionalInformation;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
