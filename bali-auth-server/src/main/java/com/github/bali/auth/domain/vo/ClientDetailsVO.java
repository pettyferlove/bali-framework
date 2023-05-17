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
public class ClientDetailsVO implements IVO {
    private static final long serialVersionUID = -4694016865378037256L;

    private String id;

    @Schema(name = "应用名称名称")
    private String applicationName;

    @Schema(name = "描述信息")
    private String description;

    @Schema(name = "域")
    private String scope;

    @Schema(name = "授权模式")
    private String authorizedGrantTypes;

    @Schema(name = "Web Client回调URL")
    private String webServerRedirectUri;

    @Schema(name = "token有效时间，单位毫秒")
    private Integer accessTokenValidity;

    @Schema(name = "刷新token有效时间，单位毫秒")
    private Integer refreshTokenValidity;

}
