package com.github.bali.attachment.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.attachment.constants.SecurityType;
import com.github.bali.attachment.constants.StorageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "附件上传信息", description = "附件上传信息VO")
public class Upload implements Serializable {

    private static final long serialVersionUID = 3346764162292691065L;

    @NotEmpty(message = "请设置文件组")
    @Schema(description = "文件组（目录）")
    private String group;

    @Schema(description = "安全类型（针对云服务提供商）")
    private SecurityType security = SecurityType.Private;

    @NotNull(message = "请指定储存方式")
    @Schema(description = "储存方式")
    private StorageType storage;

    @Schema(description = "附加参数（JSON字符串）", example = "{}")
    private String additionalParams = "{\"compress\":true}";


}
