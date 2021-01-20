package com.github.bali.auth.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.UserInfoQueryParams;
import com.github.bali.auth.domain.vo.UserOperate;
import com.github.bali.auth.entity.UserInfoView;
import com.github.bali.auth.service.IUserInfoViewService;
import com.github.bali.auth.service.IUserOperateService;
import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.core.framework.domain.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Pettyfer
 */
@RestController
@RequestMapping(ApiConstant.API_V1_PREFIX + "/user")
@Api(tags = {"用户信息维护接口"})
public class UserApi {

    private final IUserInfoViewService userInfoViewService;

    private final IUserOperateService userOperateService;

    public UserApi(IUserInfoViewService userInfoViewService, IUserOperateService userOperateService) {
        this.userInfoViewService = userInfoViewService;
        this.userOperateService = userOperateService;
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.read')")
    @GetMapping("page")
    @ApiOperation(value = "分页获取用户信息", notes = "需租户管理员权限或管理员权限和user.read域", authorizations = @Authorization(value = "oauth2"))
    public R<IPage<UserInfoView>> userPage(@ApiParam("查询参数") UserInfoQueryParams params,
                                           @ApiParam("分页参数") Page<UserInfoView> page) {
        return new R<>(userInfoViewService.page(params, page));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.read')")
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID获取用户信息详情", notes = "需租户管理员权限或管理员权限和user.operate域", authorizations = @Authorization(value = "oauth2"))
    public R<UserInfoView> get(@ApiParam("用户ID") @PathVariable String id) {
        return new R<>(userInfoViewService.getOne(Wrappers.<UserInfoView>lambdaQuery().eq(UserInfoView::getId, id)));
    }


    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.operate')")
    @PostMapping
    @ApiOperation(value = "创建用户", notes = "需租户管理员权限或管理员权限和user.operate域", authorizations = @Authorization(value = "oauth2"))
    public R<String> create(@ApiParam("用户信息") @RequestBody @Validated UserOperate user) {
        try {
            return new R<>(userOperateService.create(user));
        } catch (Exception e) {
            return new R<>(null, e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.operate')")
    @PutMapping
    @ApiOperation(value = "更新用户", notes = "需租户管理员权限或管理员权限和user.operate域", authorizations = @Authorization(value = "oauth2"))
    public R<Boolean> update(@ApiParam("用户信息") @RequestBody @Validated UserOperate user) {
        try {
            return new R<>(userOperateService.update(user));
        } catch (Exception e) {
            return new R<>(null, e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.operate')")
    @DeleteMapping("{id}")
    @ApiOperation(value = "删除用户", notes = "需租户管理员权限或管理员权限和user.operate域", authorizations = @Authorization(value = "oauth2"))
    public R<Boolean> delete(@ApiParam("用户ID") @PathVariable String id) {
        try {
            return new R<>(userOperateService.delete(id));
        } catch (Exception e) {
            return new R<>(null, e.getMessage());
        }
    }

}
