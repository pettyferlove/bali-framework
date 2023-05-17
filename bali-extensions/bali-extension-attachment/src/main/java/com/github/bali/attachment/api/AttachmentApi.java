package com.github.bali.attachment.api;

import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.domain.vo.UploadResult;
import com.github.bali.attachment.service.IAttachmentOperaService;
import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.security.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "附件操作", description = "附件操作接口")
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

    @Operation(summary = "上传附件")
    @PostMapping("upload")
    public R<UploadResult> upload(@Parameter(description = "附件信息") @Validated Upload upload, @Parameter(description = "文件") MultipartFile file) {
        return new R<>(attachmentOperaService.upload(Objects.requireNonNull(SecurityUtil.getUser()).getId(), upload, file));
    }

    @Operation(summary = "下载附件")
    @GetMapping("download/{id}")
    public void download(@Parameter(description = "附件ID") @PathVariable String id, HttpServletResponse response) throws Exception {
        attachmentOperaService.download(id, response);
    }

    @Operation(summary = "查看附件（针对图片、PDF等文件）")
    @GetMapping("view/{id}")
    public void view(@Parameter(description = "附件ID") @PathVariable String id, HttpServletResponse response) throws Exception {
        attachmentOperaService.view(id, response);
    }

    @Operation(summary = "删除附件")
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@Parameter(description = "附件ID") @PathVariable String id) {
        return new R<>(attachmentOperaService.delete(id));
    }

    @Operation(summary = "批量删除附件")
    @Deprecated
    @PostMapping("/batch/delete")
    public R<Boolean> deleteBatch(@Parameter(description = "附件ID集合") String[] ids) {
        return new R<>(attachmentOperaService.deleteBatch(ids));
    }

}
