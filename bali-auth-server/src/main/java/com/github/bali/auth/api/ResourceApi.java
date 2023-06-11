package com.github.bali.auth.api;

import com.github.bali.auth.domain.vo.UserDetails;
import com.github.bali.security.userdetails.BaliUserDetails;
import com.github.bali.security.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@RestController
@RequestMapping("/resource")
@Deprecated
@Tag(name = "第三方客户端资源接口", description = "ResourceApi")
public class ResourceApi {
    /**
     * 第三方客户端通过获取用户详细信息
     *
     * @return UserDetails
     */

    @PreAuthorize("#oauth2.hasScope('user.read')")
    @GetMapping("user-info")
    @Operation(summary = "获取用户信息")
    @Deprecated
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
}
