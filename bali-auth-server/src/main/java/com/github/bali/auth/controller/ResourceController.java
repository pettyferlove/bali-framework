package com.github.bali.auth.controller;

import com.github.bali.auth.domain.vo.UserDetailsVO;
import com.github.bali.security.userdetails.BaliUserDetails;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@RestController
@RequestMapping("/resource")
@PreAuthorize("#oauth2.hasAnyScope('resource.read','user.read')")
public class ResourceController {


    /**
     * 第三方客户端通过获取用户详细信息
     *
     * @return UserDetails
     */
    @RequestMapping("user-info")
    public UserDetailsVO userInfo() {
        BaliUserDetails user = SecurityUtil.getUser();
        return UserDetailsVO.builder()
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
