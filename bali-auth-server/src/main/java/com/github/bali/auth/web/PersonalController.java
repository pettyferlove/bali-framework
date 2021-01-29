package com.github.bali.auth.web;

import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.domain.vo.UploadResult;
import com.github.bali.attachment.service.IAttachmentOperaService;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("personal")
public class PersonalController {

    private final IAttachmentOperaService attachmentOperaService;

    public PersonalController(IAttachmentOperaService attachmentOperaService) {
        this.attachmentOperaService = attachmentOperaService;
    }

    @RequestMapping("/index")
    public String index(Model model) {
        return "personal/index";
    }



    @RequestMapping("/avatar")
    public String avatar(Model model) {
        return "personal/avatar";
    }

    @PostMapping("/avatar/upload")
    @ResponseBody
    public R<UploadResult> upload(@Validated Upload upload, MultipartFile file) {
        return new R<>(attachmentOperaService.upload(Objects.requireNonNull(SecurityUtil.getUser()).getId(), upload, file));
    }

}
