package com.github.bali.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.core.framework.domain.vo.IVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalDetails implements IVO {

    private static final long serialVersionUID = -7039822742171722657L;
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

    @Schema(name = "所属租户")
    private String tenantName;

    @Schema(name = "拥有角色")
    private List<PersonalRole> roles;

}
