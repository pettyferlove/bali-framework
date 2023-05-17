package com.github.bali.auth.api;

import com.github.bali.auth.domain.vo.ChangePassword;
import com.github.bali.auth.domain.vo.PersonalDetails;
import com.github.bali.auth.service.IUserInfoService;
import com.github.bali.auth.service.IUserService;
import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.core.framework.domain.vo.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@RestController
@RequestMapping(ApiConstant.API_V1_PREFIX + "/personal")
@Tag(name = "个人信息接口", description = "PersonalApi")
public class PersonalApi {

    private final IUserInfoService userInfoService;

    private final IUserService userService;

    public PersonalApi(IUserInfoService userInfoService, IUserService userService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
    }

    @GetMapping("/info")
    @PreAuthorize("#oauth2.hasScope('user.read')")
    @Operation(summary = "获取个人信息")
    public R<PersonalDetails> get() {
        return new R<>(userInfoService.getDetails());
    }

    @PutMapping("/info")
    @Operation(summary = "更新个人信息")
    public R<Boolean> update(PersonalDetails details) {
        return new R<>(userInfoService.updateDetails(details));
    }

    @PutMapping("/change/password")
    @Operation(summary = "修改密码")
    public R<Boolean> changePassword(@Validated ChangePassword changePassword) {
        return new R<>(userService.changePassword(changePassword));
    }


}
