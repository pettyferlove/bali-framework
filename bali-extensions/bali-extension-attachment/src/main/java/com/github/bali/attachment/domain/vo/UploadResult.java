package com.github.bali.attachment.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@Schema(name = "附件结果", description = "附件上传结果VO")
public class UploadResult implements Serializable {
    private static final long serialVersionUID = 6121441712776319710L;

    @Schema(description = "附件ID")
    private String fileId;

    @Schema(description = "附件名")
    private String fileName;

    @Schema(description = "访问地址，针对可公有读")
    private String url;

    @Schema(description = "附加信息（JSON数组）")
    private String additionalData;

}
