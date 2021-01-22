package com.github.bali.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.core.framework.domain.vo.IVO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="ChangePasswordVO对象", description="变更密码")
public class ChangePassword implements IVO {
    private static final long serialVersionUID = 157652993416293114L;

    //@NotNull(message = "原始密码不可为空")
    private String oldPassword;

    //@NotNull(message = "新密码不可为空")
    private String newPassword;

    //@NotNull(message = "重复新密码不可为空")
    private String repeatPassword;
}
