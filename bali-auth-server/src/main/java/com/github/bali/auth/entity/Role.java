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

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 用户角色
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
@TableName("uc_role")
@Schema(name="Role对象", description="用户角色")
public class Role extends BaseEntity<Role> {

    private static final long serialVersionUID = 1L;

    @Schema(name = "角色")
    @NotEmpty(message = "角色不能为空")
    private String role;

    @Schema(name = "角色名称")
    @NotEmpty(message = "角色名称为空")
    private String roleName;

    @Schema(name = "描述")
    private String description;

    @Schema(name = "排序号")
    private Integer sort;

    @Schema(name = "状态 1有效 0无效 默认为1")
    private Integer status;

    @Schema(name = "租户ID")
    private String tenantId;

}
