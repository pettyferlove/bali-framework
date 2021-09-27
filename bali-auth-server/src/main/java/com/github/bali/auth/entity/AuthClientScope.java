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
 * 客户端域
 * </p>
 *
 * @author Petty
 * @since 2021-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("uc_auth_client_scope")
@ApiModel(value="AuthClientScope对象", description="客户端域")
public class AuthClientScope extends BaseEntity<AuthClientScope> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "域名，用于客户端进行权限判断")
    private String scope;

    @ApiModelProperty(value = "是否自动授权")
    private Boolean auto;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

}
