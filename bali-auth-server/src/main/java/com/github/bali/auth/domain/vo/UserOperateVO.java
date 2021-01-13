package com.github.bali.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.core.framework.domain.vo.IVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Pettyfer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserOperateVO implements IVO {
    private static final long serialVersionUID = 5030762540026814382L;

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "用户登录名")
    private String loginId;

    @ApiModelProperty(value = "账号密码")
    private String password;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

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


}
