package com.github.bali.attachment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.bali.persistence.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Petty
 * @since 2021-01-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("extension_attachment_info")
@ApiModel(value = "AttachmentInfo对象", description = "")
public class AttachmentInfo extends BaseEntity<AttachmentInfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件大小")
    private Long size;

    @ApiModelProperty(value = "储存类型")
    private Integer storageType;

    @ApiModelProperty(value = "MD5值")
    private String md5;

    @ApiModelProperty(value = "地址")
    private String path;

    private String tenantId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
