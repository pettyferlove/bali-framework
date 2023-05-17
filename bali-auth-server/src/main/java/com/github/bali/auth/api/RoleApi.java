package com.github.bali.auth.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.entity.RoleView;
import com.github.bali.auth.service.IRoleService;
import com.github.bali.auth.service.IRoleViewService;
import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.core.framework.domain.vo.R;
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
@RequestMapping(ApiConstant.API_V1_PREFIX + "/role")
@Tag(name = "角色管理接口", description = "RoleApi")
public class RoleApi {

    private final IRoleService roleService;

    private final IRoleViewService roleViewService;

    public RoleApi(IRoleService roleService, IRoleViewService roleViewService) {
        this.roleService = roleService;
        this.roleViewService = roleViewService;
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('resource.read')")
    @GetMapping("all")
    @Operation(summary = "查询全部角色")
    public R<List<Role>> allRole() {
        return new R<>(roleService.list());
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('resource.read')")
    @GetMapping("page")
    @Operation(summary = "查询角色列表")
    public R<IPage<RoleView>> page(@RequestParam(required = false) String role,
                                       @RequestParam(required = false) String roleName,
                                       Page<RoleView> page) {
        return new R<>(roleViewService.page(role, roleName, page));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('resource.operate')")
    @PostMapping
    @Operation(summary = "添加角色")
    public R<String> add(@RequestBody @Validated Role role) {
        return new R<>(roleService.create(role));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('resource.operate')")
    @PutMapping
    @Operation(summary = "更新角色")
    public R<Boolean> update(@RequestBody @Validated Role role) {
        return new R<>(roleService.update(role));
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN')&&#oauth2.hasScope('resource.operate')")
    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(roleService.delete(id));
    }


}
