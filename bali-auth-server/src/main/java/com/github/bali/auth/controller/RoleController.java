package com.github.bali.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.service.IRoleService;
import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.security.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author Petty
 */
@Api(value = "角色信息", tags = {"角色信息接口"})
@RestController
@PreAuthorize("#oauth2.hasScope('application')")
@RequestMapping(ApiConstant.API_V1_PREFIX + "/role")
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "获取角色列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("page")
    public R<IPage<Role>> page(Role role, Page<Role> page) {
        return new R<>(roleService.page(role, page));
    }

    @ApiOperation(value = "获取角色详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<Role> get(@PathVariable String id) {
        return new R<>(roleService.get(id));
    }

    @ApiOperation(value = "创建角色", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping
    public R<String> create(Role role) {
        return new R<>(roleService.create(Objects.requireNonNull(SecurityUtil.getUser()).getId(), role));
    }

    @ApiOperation(value = "更新角色", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping
    public R<Boolean> update(Role role) {
        return new R<>(roleService.update(Objects.requireNonNull(SecurityUtil.getUser()).getId(), role));
    }

    @ApiOperation(value = "删除角色", notes = "删除角色将会删除与角色相关的关联数据，同时需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(roleService.delete(id));
    }


    @ApiOperation(value = "获取系统全部角色", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("/all")
    public R<List<Role>> all() {
        return new R<>(roleService.list());
    }

    @ApiOperation(value = "获取用户下角色ID", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "userId", value = "userId", dataTypeClass = String.class)
    })
    @GetMapping("/user/{userId}")
    public R<List<String>> user(@PathVariable String userId) {
        return new R<>(roleService.findRoleKeyByUserId(userId));
    }

    @ApiOperation(value = "为用户增加角色", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "userId", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "ids", value = "ids", dataTypeClass = String[].class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping("/user/add")
    public R<Boolean> userAdd(String userId, String[] ids) {
        return new R<>(roleService.addUserRole(userId, ids));
    }

    @ApiOperation(value = "为用户删除角色", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "userId", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "ids", value = "ids", dataTypeClass = String[].class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping("/user/delete")
    public R<Boolean> userDelete(String userId, String[] ids) {
        return new R<>(roleService.deleteUserRole(userId, ids));
    }


    @ApiOperation(value = "检测角色是否存在", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "role", value = "role", dataTypeClass = String.class)
    })
    @GetMapping("/check/{role}")
    public R<Boolean> check(@PathVariable String role) {
        return new R<>(roleService.check(role));
    }

}
