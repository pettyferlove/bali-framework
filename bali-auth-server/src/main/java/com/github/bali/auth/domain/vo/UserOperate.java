package com.github.bali.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.core.framework.domain.vo.IVO;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class UserOperate implements IVO {
    private static final long serialVersionUID = 5030762540026814382L;

    @Schema(name = "用户ID")
    private String id;

    @Schema(name = "角色IDs，可指定多个（可选）")
    private String roleIds;

    @Schema(name = "用户登录名")
    private String loginId;

    @Schema(name = "账号密码")
    private String password;

    @Schema(name = "租户ID")
    private String tenantId;

    @Schema(name = "是否有效 0：无效 1：有效 8：凭证无效 9：锁定")
    private Integer status;

    @Schema(name = "姓名")
    private String userName;

    @Schema(name = "用户昵称")
    private String nickName;

    @Schema(name = "性别")
    private Integer userSex;

    @Schema(name = "生日")
    private LocalDateTime userBorn;

    @Schema(name = "用户头像")
    private String userAvatar;

    @Schema(name = "电子邮件")
    private String email;

    @Schema(name = "居住地址")
    private String userAddress;

    @Schema(name = "移动电话")
    private String mobileTel;

    @Schema(name = "用户联系电话")
    private String phoneTel;

    @Schema(name = "用户证件类型")
    private String userIdenType;

    @Schema(name = "证件ID")
    private String userIden;

    @Schema(name = "工作单位")
    private String organization;

    @Schema(name = "学历")
    private String education;

    @Schema(name = "职称")
    private String positionalTitle;

    @Schema(name = "政治面貌")
    private String politicalStatus;

    @Schema(name = "毕业学校")
    private String graduatedSchool;

    @Schema(name = "毕业年份")
    private String graduatedYear;

    @Schema(name = "所学专业")
    private String major;

    @Schema(name = "是否实名认证")
    private Integer verified;

    @Schema(name = "行政区划CODE")
    private String regionId;


}
