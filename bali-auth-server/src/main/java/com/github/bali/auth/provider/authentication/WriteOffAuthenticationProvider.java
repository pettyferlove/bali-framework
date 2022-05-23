package com.github.bali.auth.provider.authentication;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.bali.security.constants.UserChannelType;
import com.github.bali.security.userdetails.BaliUserDetails;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pettyfer
 * @version 1.0
 * @description: 微信登录认证
 * @date 2022/1/21 9:42
 */
@Slf4j
@Component
public class WriteOffAuthenticationProvider implements AuthenticationProvider {

    private final RestTemplate restTemplate;

    public static final String REMOTE_URI = "http://127.0.0.1:8078/api/v1/activity-write-off-auth/login/{0}/{1}";

    public WriteOffAuthenticationProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @SneakyThrows
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();
        String authorizationCode = (String) authentication.getCredentials();
        URI uri = new URI(MessageFormat.format(REMOTE_URI, principal, authorizationCode));
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(uri, JSONObject.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JSONObject userInfo = responseEntity.getBody();
            if (ObjectUtil.isNotNull(userInfo)) {
                List<String> role = new ArrayList<>();
                role.add("WRITE_OFF");
                UserDetails userDetails = BaliUserDetails.builder()
                        .id(userInfo.getString("id"))
                        .username(userInfo.getString("name"))
                        .nickname(userInfo.getString("username"))
                        .channel(UserChannelType.WECHAT_MINI_PROGRAM.getValue())
                        .tenant(userInfo.getString("tenantId"))
                        .roles(role)
                        .status(userInfo.getInteger("status"))
                        .build();
                return new WriteOffAuthenticationToken(userDetails, "N/A", userDetails.getAuthorities());
            } else {
                throw new UsernameNotFoundException("用户不存在");
            }
        } else {
            throw new RuntimeException("加载用户信息失败");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (WriteOffAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
