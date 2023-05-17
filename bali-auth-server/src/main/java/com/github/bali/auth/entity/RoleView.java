package com.github.bali.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author Petty
 * @since 2021-01-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("v_role_view")
@Schema(name="RoleView对象", description="VIEW")
public class RoleView extends Model<RoleView> {

    private static final long serialVersionUID = 1L;

    @Schema(name = "角色ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Schema(name = "角色")
    private String role;

    @Schema(name = "角色名称")
    private String roleName;

    @Schema(name = "描述")
    private String description;

    @Schema(name = "排序号")
    private Integer sort;

    @Schema(name = "状态 1有效 0无效 默认为1")
    private Integer status;

    @Schema(name = "租户ID")
    private String tenantId;

    @Schema(name = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "修改时间")
    private LocalDateTime modifyTime;

}
