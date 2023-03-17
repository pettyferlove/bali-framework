package com.github.bali.auth.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.ChangePasswordVO;
import com.github.bali.auth.domain.vo.UserInfoQueryParams;
import com.github.bali.auth.domain.vo.UserOperate;
import com.github.bali.auth.entity.UserInfoView;
import com.github.bali.auth.service.IUserInfoViewService;
import com.github.bali.auth.service.IUserOperateService;
import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.security.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Pettyfer
 */
@RestController
@RequestMapping(ApiConstant.API_V1_PREFIX + "/user")
@Tag(name = "用户信息管理接口", description = "UserApi")
public class UserApi {

    private final IUserInfoViewService userInfoViewService;

    private final IUserOperateService userOperateService;

    public UserApi(IUserInfoViewService userInfoViewService, IUserOperateService userOperateService) {
        this.userInfoViewService = userInfoViewService;
        this.userOperateService = userOperateService;
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.read')")
    @GetMapping("page")
    @Operation(summary = "查询用户列表")
    public R<IPage<UserInfoView>> page(UserInfoQueryParams params,
                                           Page<UserInfoView> page) {
        return new R<>(userInfoViewService.page(params, page));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.read')")
    @GetMapping("page/role/{role}")
    @Operation(summary = "根据角色查询用户列表")
    public R<IPage<UserInfoView>> userPageByRole(UserInfoQueryParams params,
                                                 Page<UserInfoView> page,
                                                 @PathVariable String role,
                                                 String excludeUserIds) {
        return new R<>(userInfoViewService.pageByRole(params, page, role, excludeUserIds));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.read')")
    @GetMapping("{id}")
    @Operation(summary = "获取用户信息")
    public R<UserInfoView> get(@PathVariable String id) {
        return new R<>(userInfoViewService.getOne(Wrappers.<UserInfoView>lambdaQuery().eq(UserInfoView::getId, id)));
    }


    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.operate')")
    @PostMapping
    @Operation(summary = "创建用户")
    public R<String> create(@RequestBody @Validated UserOperate user) {
        return new R<>(userOperateService.create(user));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.operate')")
    @PutMapping
    @Operation(summary = "修改用户信息")
    public R<Boolean> update(@RequestBody @Validated UserOperate user) {
        return new R<>(userOperateService.update(user));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.operate')")
    @DeleteMapping("{id}")
    @Operation(summary = "删除用户")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(userOperateService.delete(id));
    }

    @Deprecated
    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.operate')")
    @DeleteMapping("batch/{ids}")
    @Operation(summary = "批量删除用户")
    public R<Boolean> batchDelete(@PathVariable String ids) {
        return new R<>(userOperateService.batchDelete(ids));
    }

    @GetMapping(value = "{id}/role")
    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.read')")
    @Operation(summary = "加载用户角色")
    public R<List<String>> loadUserRole(@PathVariable String id) {
        return new R<>(userOperateService.loadUserRoleIds(id));
    }

    @PutMapping(value = "{id}/role")
    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('user.operate')")
    @Operation(summary = "更新用户角色")
    public R<Boolean> updateUserRole(@PathVariable String id, @RequestParam(defaultValue = "") String roleIds) {
        return new R<>(userOperateService.updateUserRole(id, roleIds));
    }

    @PutMapping("reset/password")
    @PreAuthorize("hasAnyRole('TENANT_ADMIN')&&#oauth2.hasScope('user.operate')")
    @Operation(summary = "重置用户密码")
    public R<Boolean> resetPassword(@RequestParam(defaultValue = "") String ids,@RequestParam String password) {
        return new R<>(userOperateService.resetPassword(ids, password));
    }

    @RequestMapping(value = "change/password", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('user.operate')")
    @Operation(summary = "修改用户密码")
    public R<Boolean> changePassword(@RequestBody ChangePasswordVO changePassword) {
        return new R<>(userOperateService.changePassword(SecurityUtil.getUser().getId(), changePassword));
    }

}
