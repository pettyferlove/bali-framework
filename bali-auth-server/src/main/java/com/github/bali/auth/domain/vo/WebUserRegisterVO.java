package com.github.bali.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.core.framework.domain.vo.IVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebUserRegisterVO implements IVO {
    private static final long serialVersionUID = 999821014362807723L;

    @ApiModelProperty(value = "用户登录名")
    private String loginId;

    @ApiModelProperty(value = "账号密码")
    private String password;

    @ApiModelProperty(value = "重复")
    private String repeatPassword;


}
