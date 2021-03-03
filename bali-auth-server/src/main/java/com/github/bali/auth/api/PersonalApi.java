package com.github.bali.auth.api;

import com.github.bali.auth.domain.vo.ChangePassword;
import com.github.bali.auth.domain.vo.PersonalDetails;
import com.github.bali.auth.service.IUserInfoService;
import com.github.bali.auth.service.IUserService;
import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.core.framework.domain.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
@Api(tags = {"个人信息维护接口"})
public class PersonalApi {

    private final IUserInfoService userInfoService;

    private final IUserService userService;

    public PersonalApi(IUserInfoService userInfoService, IUserService userService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
    }

    @ApiOperation(value = "获取个人详细信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("/info")
    public R<PersonalDetails> get() {
        return new R<>(userInfoService.getDetails());
    }

    @ApiOperation(value = "更新个人详细信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @PutMapping("/info")
    public R<Boolean> update(PersonalDetails details) {
        return new R<>(userInfoService.updateDetails(details));
    }

    @ApiOperation(value = "更新密码", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @PutMapping("/change/password")
    public R<Boolean> changePassword(@Validated ChangePassword changePassword) {
        return new R<>(userService.changePassword(changePassword));
    }


}
