package com.github.bali.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * VIEW
 * </p>
 *
 * @author Petty
 * @since 2021-01-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("v_user_info_view")
@ApiModel(value="UserInfoView对象", description="VIEW")
public class UserInfoView extends Model<UserInfoView> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "登录ID")
    private String loginId;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "所属客户端（仅对公开注册的用户生效）")
    private String clientId;

    @ApiModelProperty(value = "所属客户端名称（仅对公开注册的用户生效）")
    private String applicationName;

    @ApiModelProperty(value = "用户所属渠道")
    private String userChannel;

    @ApiModelProperty(value = "是否有效 0：无效 1：有效 8：凭证无效 9：锁定")
    private Integer status;

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

    @ApiModelProperty(value = "创建人")
    private String creator;

    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    private String modifierName;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;

}
