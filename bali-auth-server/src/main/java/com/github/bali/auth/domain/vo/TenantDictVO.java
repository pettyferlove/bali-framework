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
public class TenantDictVO implements IVO {
    private static final long serialVersionUID = -2241616689528365503L;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "租户名")
    private String tenantName;

}
