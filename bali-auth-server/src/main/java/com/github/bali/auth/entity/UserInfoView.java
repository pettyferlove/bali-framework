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
 * @since 2021-01-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("v_user_info_view")
@Schema(name="UserInfoView对象", description="VIEW")
public class UserInfoView extends Model<UserInfoView> {

    private static final long serialVersionUID = 1L;

    @Schema(name = "用户ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Schema(name = "登录ID")
    private String loginId;

    @Schema(name = "租户ID")
    private String tenantId;

    @Schema(name = "所属客户端（仅对公开注册的用户生效）")
    private String clientId;

    @Schema(name = "所属客户端名称（仅对公开注册的用户生效）")
    private String applicationName;

    @Schema(name = "用户所属渠道")
    private String userChannel;

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

    @Schema(name = "创建人")
    private String creator;

    private String creatorName;

    @Schema(name = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "修改人")
    private String modifier;

    private String modifierName;

    @Schema(name = "修改时间")
    private LocalDateTime modifyTime;

}
