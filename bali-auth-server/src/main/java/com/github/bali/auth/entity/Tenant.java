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
 * 租户信息
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
@TableName("uc_tenant")
@Schema(name="Tenant对象", description="租户信息")
public class Tenant extends BaseEntity<Tenant> {

    private static final long serialVersionUID = 1L;

    @Schema(name = "租户ID")
    private String tenantId;

    @Schema(name = "租户名")
    private String tenantName;

    @Schema(name = "联系人")
    private String linkMan;

    @Schema(name = "联系电话")
    private String contactNumber;

    @Schema(name = "地址")
    private String address;

    @Schema(name = "备注")
    private String remark;

    @Schema(name = "电子邮件")
    private String email;

    @Schema(name = "是否有效 0 无效 1 有效")
    private Integer status;

}
