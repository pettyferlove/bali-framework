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
@Schema(name="AuthClientScope对象", description="客户端域")
public class AuthClientScope extends BaseEntity<AuthClientScope> {

    private static final long serialVersionUID = 1L;

    @Schema(name = "域名，用于客户端进行权限判断")
    private String scope;

    @Schema(name = "是否自动授权")
    private Boolean auto;

    @Schema(name = "描述信息")
    private String description;

    @Schema(name = "租户ID")
    private String tenantId;

}
