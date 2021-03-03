package com.github.bali.auth.web;

import com.github.bali.attachment.domain.vo.Upload;
import com.github.bali.attachment.domain.vo.UploadResult;
import com.github.bali.attachment.service.IAttachmentOperaService;
import com.github.bali.auth.domain.vo.PersonalDetails;
import com.github.bali.auth.service.IUserInfoService;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.security.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author Pettyfer
 */
@Controller
@RequestMapping("personal")
public class PersonalController {

    private final IAttachmentOperaService attachmentOperaService;

    private final IUserInfoService userInfoService;

    public PersonalController(IAttachmentOperaService attachmentOperaService, IUserInfoService userInfoService) {
        this.attachmentOperaService = attachmentOperaService;
        this.userInfoService = userInfoService;
    }

    @RequestMapping("/index")
    public String index(Model model) {
        PersonalDetails details = userInfoService.getDetails();
        model.addAttribute("details", details);
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

    @PutMapping("/info")
    @ResponseBody
    public R<Boolean> update(@RequestBody PersonalDetails details) {
        try {
            return new R<>(userInfoService.updateDetails(details));
        } catch (Exception e) {
            e.printStackTrace();
            return new R<>(null, e.getMessage());
        }
    }

}
