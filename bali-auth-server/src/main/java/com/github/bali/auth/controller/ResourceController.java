package com.github.bali.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.bali.auth.domain.vo.UserDetails;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.entity.RoleView;
import com.github.bali.auth.service.IRoleService;
import com.github.bali.auth.service.IRoleViewService;
import com.github.bali.security.userdetails.BaliUserDetails;
import com.github.bali.security.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Petty
 */
@RestController
@RequestMapping("/resource")
@Api(tags = {"通用资源接口"})
public class ResourceController {


    private final IRoleService roleService;

    private final IRoleViewService roleViewService;

    public ResourceController(IRoleService roleService, IRoleViewService roleViewService) {
        this.roleService = roleService;
        this.roleViewService = roleViewService;
    }

    /**
     * 第三方客户端通过获取用户详细信息
     *
     * @return UserDetails
     */

    @PreAuthorize("#oauth2.hasScope('user.read')")
    @GetMapping("user-info")
    @ApiOperation(value = "获取个人信息", notes = "需user.read域")
    public UserDetails userInfo() {
        BaliUserDetails user = SecurityUtil.getUser();
        return UserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .status(user.getStatus())
                .tenant(user.getTenant())
                .roles(user.getRoles())
                .enabled(user.isEnabled())
                .accountNonLocked(user.isAccountNonLocked())
                .accountNonExpired(user.isAccountNonExpired())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .build();
    }

    @PreAuthorize("hasRole('TENANT_ADMIN')&&#oauth2.hasScope('resource.read')")
    @GetMapping("role/all")
    @ApiOperation(value = "获取全部角色信息", notes = "需租户管理员权限和resource.read域")
    public List<Role> allRole() {
        return roleService.list();
    }

    @PreAuthorize("hasRole('TENANT_ADMIN')&&#oauth2.hasScope('resource.read')")
    @GetMapping("role/page")
    @ApiOperation(value = "分页获取角色信息", notes = "需租户管理员权限和resource.read域")
    public IPage<RoleView> rolePage(String role, String roleName, Page<RoleView> page) {
        return roleViewService.page(role, roleName, page);
    }

}
