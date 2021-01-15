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
public class ClientDetailsVO implements IVO {
    private static final long serialVersionUID = -4694016865378037256L;

    private String id;

    @ApiModelProperty(value = "应用名称名称")
    private String applicationName;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "域")
    private String scope;

    @ApiModelProperty(value = "授权模式")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "Web Client回调URL")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "token有效时间，单位毫秒")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "刷新token有效时间，单位毫秒")
    private Integer refreshTokenValidity;

}
