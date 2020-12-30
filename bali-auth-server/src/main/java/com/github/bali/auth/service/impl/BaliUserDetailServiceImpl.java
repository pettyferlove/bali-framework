package com.github.bali.auth.service.impl;

import com.github.bali.auth.constants.SecurityConstant;
import com.github.bali.auth.service.OAuth2UserDetailsService;
import com.github.bali.auth.userdetails.BaliUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Petty
 */
@Slf4j
@Service
public class BaliUserDetailServiceImpl implements OAuth2UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public BaliUserDetailServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        roles.add("ADMIN");
        List<String> permissions = new ArrayList<>();
        permissions.add("user:read");
        permissions.add("user:update");
        permissions.add("user:delete");
        permissions.add("user:create");
        String encode = new MessageDigestPasswordEncoder("SHA-256").encode("123456");
        return BaliUserDetails.builder()
                .id(UUID.randomUUID().toString())
                .username("pettyfer")
                .password(encode)
                .status(SecurityConstant.STATUS_NORMAL)
                .nickname("AlexPettyfer")
                .roles(roles)
                .permissions(permissions)
                .email("pettyferlove@live.cn")
                .tenant("000000001")
                .avatar("https://bali-attachment.oss-cn-shanghai.aliyuncs.com/bali/avatar/6fdbd6c29f074e38abe1ad6929351192.png")
                .build();
    }
}
