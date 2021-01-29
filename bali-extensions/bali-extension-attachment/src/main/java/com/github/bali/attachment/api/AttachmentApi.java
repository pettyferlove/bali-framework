package com.github.bali.attachment.api;

import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.domain.vo.UploadResult;
import com.github.bali.attachment.service.IAttachmentOperaService;
import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.security.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * @author Petty
 */
@Api(value = "附件操作", tags = {"附件操作接口"})
@Slf4j
@RestController
@RequestMapping(ApiConstant.API_V1_PREFIX + "/attachment")
@ConditionalOnProperty(value = "attachment.api", havingValue = "enable")
@ConditionalOnWebApplication
public class AttachmentApi {

    private final IAttachmentOperaService attachmentOperaService;

    public AttachmentApi(IAttachmentOperaService attachmentOperaService) {
        this.attachmentOperaService = attachmentOperaService;
    }

    @ApiOperation(value = "上传附件", authorizations = @Authorization(value = "oauth2"))
    @PostMapping("upload")
    public R<UploadResult<?>> upload(@ApiParam("附件信息") @Validated Upload upload, @ApiParam("文件") MultipartFile file) {
        return new R<UploadResult<?>>(attachmentOperaService.upload(Objects.requireNonNull(SecurityUtil.getUser()).getId(), upload, file));
    }

    @ApiOperation(value = "下载附件", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("download/{id}")
    public void download(@ApiParam("附件ID") @PathVariable String id, HttpServletResponse response) throws Exception {
        attachmentOperaService.download(id, response);
    }

    @ApiOperation(value = "查看附件（针对图片、PDF等文件）", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("view/{id}")
    public void view(@ApiParam("附件ID") @PathVariable String id, HttpServletResponse response) throws Exception {
        attachmentOperaService.view(id, response);
    }

    @ApiOperation(value = "删除附件", authorizations = @Authorization(value = "oauth2"))
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@ApiParam("附件ID") @PathVariable String id) {
        return new R<>(attachmentOperaService.delete(id));
    }

    @ApiOperation(value = "批量删除附件", authorizations = @Authorization(value = "oauth2"))
    @Deprecated
    @PostMapping("/batch/delete")
    public R<Boolean> deleteBatch(@ApiParam("附件ID集合") String[] ids) {
        return new R<>(attachmentOperaService.deleteBatch(ids));
    }

}
