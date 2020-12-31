package com.github.bali.auth.endpoint;

import com.github.bali.auth.domain.vo.UserInfo;
import com.github.bali.auth.userdetails.BaliUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Pettyfer
 */
@FrameworkEndpoint
public class UserInfoEndpoint {

    @RequestMapping(value = "/resource/user-info")
    @ResponseBody
    public UserInfo user(Authentication authentication) {
        BaliUserDetails userDetails = (BaliUserDetails) authentication.getPrincipal();
        return UserInfo.builder()
                .id(userDetails.getId())
                .name(userDetails.getUsername())
                .nickname(userDetails.getNickname())
                .avatar(userDetails.getAvatar())
                .email(userDetails.getEmail())
                .tenant(userDetails.getTenant())
                .roles(userDetails.getRoles())
                .permissions(userDetails.getPermissions())
                .build();
    }

}
