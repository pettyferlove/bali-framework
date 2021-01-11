package com.github.bali.auth.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.Tenant;
import com.github.bali.auth.service.ITenantService;
import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.security.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 租户信息 接口控制器
 * </p>
 *
 * @author Petty
 * @since 2019-07-13
 */
@Api(value = "租户信息", tags = {"租户信息接口"})
@RestController
@PreAuthorize("#oauth2.hasScope('application')")
@RequestMapping(ApiConstant.API_V1_PREFIX + "/tenant")
public class TenantController {
    
    private final ITenantService tenantService;

    public TenantController(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    @ApiOperation(value = "获取租户信息列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("page")
    public R<IPage<Tenant>> page(Tenant systemTenant, Page<Tenant> page) {
        return new R<>(tenantService.page(systemTenant, page));
    }

    @ApiOperation(value = "获取全部租户信息表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("all")
    public R<List<Tenant>> all() {
        return new R<>(tenantService.all());
    }


    @ApiOperation(value = "获取租户信息详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<Tenant> get(@PathVariable String id) {
        return new R<>(tenantService.get(id));
    }

    @ApiOperation(value = "创建租户信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public R<String> create(Tenant systemTenant) {
        return new R<>(tenantService.create(Objects.requireNonNull(SecurityUtil.getUser()).getId(), systemTenant));
    }

    @ApiOperation(value = "更新租户信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping
    public R<Boolean> update(Tenant systemTenant) {
        return new R<>(tenantService.update(Objects.requireNonNull(SecurityUtil.getUser()).getId(), systemTenant));
    }

    @ApiOperation(value = "删除租户信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(tenantService.delete(id));
    }


    @ApiOperation(value = "更新租户状态", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping(value = "/status")
    public R<Boolean> changeStatus(Tenant tenant) {
        return new R<>(tenantService.changeStatus(Objects.requireNonNull(SecurityUtil.getUser()).getId(), tenant));
    }

    @ApiOperation(value = "检测租户是否存在", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "name", value = "name", dataTypeClass = String.class)
    })
    @GetMapping("/check/{tenantId}")
    public R<Boolean> check(@PathVariable String tenantId) {
        return new R<>(tenantService.check(tenantId));
    }

}
