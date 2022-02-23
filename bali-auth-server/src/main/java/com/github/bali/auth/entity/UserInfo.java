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
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息
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
@TableName("uc_user_info")
@ApiModel(value="UserInfo对象", description="用户信息")
public class UserInfo extends BaseEntity<UserInfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "性别")
    private Integer userSex;

    @ApiModelProperty(value = "生日")
    private LocalDateTime userBorn;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "居住地址")
    private String userAddress;

    @ApiModelProperty(value = "移动电话")
    private String mobileTel;

    @ApiModelProperty(value = "用户联系电话")
    private String phoneTel;

    @ApiModelProperty(value = "用户证件类型")
    private String userIdenType;

    @ApiModelProperty(value = "证件ID")
    private String userIden;

    @ApiModelProperty(value = "工作单位")
    private String organization;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "职称")
    private String positionalTitle;

    @ApiModelProperty(value = "政治面貌")
    private String politicalStatus;

    @ApiModelProperty(value = "毕业学校")
    private String graduatedSchool;

    @ApiModelProperty(value = "毕业年份")
    private String graduatedYear;

    @ApiModelProperty(value = "所学专业")
    private String major;

    @ApiModelProperty(value = "是否实名认证")
    private Integer verified;

    @ApiModelProperty(value = "行政区划CODE")
    private String regionId;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "所属客户端（仅对公开注册的用户生效）")
    private String clientId;

}
