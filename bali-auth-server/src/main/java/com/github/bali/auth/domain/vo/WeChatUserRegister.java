package com.github.bali.auth.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.core.framework.domain.vo.IVO;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "微信OpenID")
    private String openId;

    @Schema(name = "微信企业组织ID")
    private String unionId;

    @Schema(name = "头像地址")
    private String avatarUrl;

    @Schema(name = "城市")
    private String city;

    @Schema(name = "国家")
    private String country;

    @Schema(name = "省份")
    private String province;

    @Schema(name = "语言")
    private String language;

    @Schema(name = "微信昵称")
    private String nickName;

    @Schema(name = "性别")
    private Integer gender;

}
