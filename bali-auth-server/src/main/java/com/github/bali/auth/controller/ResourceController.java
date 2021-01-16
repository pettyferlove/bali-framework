package com.github.bali.auth.controller;

import com.github.bali.auth.domain.vo.UserDetails;
import com.github.bali.auth.entity.Role;
import com.github.bali.auth.service.IRoleService;
import com.github.bali.security.userdetails.BaliUserDetails;
import com.github.bali.security.utils.SecurityUtil;
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
public class ResourceController {


    private final IRoleService roleService;

    public ResourceController(IRoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 第三方客户端通过获取用户详细信息
     *
     * @return UserDetails
     */

    @PreAuthorize("hasRole('TENANT_ADMIN')&&#oauth2.hasScope('user.read')")
    @GetMapping("user-info")
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
    public List<Role> allRole() {
        return roleService.list();
    }


}
