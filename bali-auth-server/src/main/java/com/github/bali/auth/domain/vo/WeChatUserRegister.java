package com.github.bali.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.core.framework.domain.vo.IVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pettyfer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeChatUserRegister implements IVO {
    private static final long serialVersionUID = 6921148356787747955L;

    @ApiModelProperty(value = "微信OpenID")
    private String openId;

    @ApiModelProperty(value = "微信企业组织ID")
    private String unionId;

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "微信昵称")
    private String nickName;

    @ApiModelProperty(value = "性别")
    private Integer gender;

}
