package com.github.bali.auth.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.entity.RoleView;
import com.github.bali.auth.service.IRoleService;
import com.github.bali.auth.service.IRoleViewService;
import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.core.framework.domain.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Pettyfer
 */
@RestController
@RequestMapping(ApiConstant.API_V1_PREFIX + "/role")
@Api(tags = {"角色信息维护接口"})
public class RoleApi {

    private final IRoleService roleService;

    private final IRoleViewService roleViewService;

    public RoleApi(IRoleService roleService, IRoleViewService roleViewService) {
        this.roleService = roleService;
        this.roleViewService = roleViewService;
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('resource.read')")
    @GetMapping("all")
    @ApiOperation(value = "获取全部角色信息", notes = "需租户管理员权限或管理员权限和resource.read域", authorizations = @Authorization(value = "oauth2"))
    public R<List<Role>> allRole() {
        return new R<>(roleService.list());
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('resource.read')")
    @GetMapping("page")
    @ApiOperation(value = "分页获取角色信息", notes = "需租户管理员权限或管理员权限和resource.read域", authorizations = @Authorization(value = "oauth2"))
    public R<IPage<RoleView>> rolePage(@ApiParam("角色") @RequestParam(required = false) String role,
                                       @ApiParam("角色名") @RequestParam(required = false) String roleName,
                                       @ApiParam("分页参数") Page<RoleView> page) {
        return new R<>(roleViewService.page(role, roleName, page));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('resource.operate')")
    @PostMapping
    @ApiOperation(value = "添加角色", notes = "需租户管理员权限或管理员权限和resource.operate域", authorizations = @Authorization(value = "oauth2"))
    public R<String> add(@ApiParam @RequestBody @Validated Role role) {
        return new R<>(roleService.create(role));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('resource.operate')")
    @PutMapping
    @ApiOperation(value = "修改角色", notes = "需租户管理员权限或管理员权限和resource.operate域", authorizations = @Authorization(value = "oauth2"))
    public R<Boolean> update(@ApiParam @RequestBody @Validated Role role) {
        return new R<>(roleService.update(role));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('resource.operate')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除角色", notes = "需租户管理员权限或管理员权限和resource.operate域", authorizations = @Authorization(value = "oauth2"))
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(roleService.delete(id));
    }


}
