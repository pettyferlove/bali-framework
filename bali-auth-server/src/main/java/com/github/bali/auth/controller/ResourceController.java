package com.github.bali.auth.controller;

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
@PreAuthorize("#oauth2.hasScope('resource.read')")
public class ResourceController {


    /**
     * 第三方客户端通过获取用户详细信息
     *
     * @return UserDetails
     */
    @RequestMapping("user-info")
    public BaliUserDetails userInfo() {
        return SecurityUtil.getUser();
    }


}
