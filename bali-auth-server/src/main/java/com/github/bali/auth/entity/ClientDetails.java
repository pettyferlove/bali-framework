package com.github.bali.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.bali.persistence.entity.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
@TableName("uc_client_details")
@ApiModel(value="ClientDetails对象", description="终端信息")
public class ClientDetails extends BaseEntity<ClientDetails> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户端名称")
    private String clientId;

    @ApiModelProperty(value = "客户端资源集")
    private String resourceIds;

    @ApiModelProperty(value = "客户端密钥")
    private String clientSecret;

    @ApiModelProperty(value = "域")
    private String scope;

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

    @ApiModelProperty(value = "自动授权")
    private String autoApprove;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
