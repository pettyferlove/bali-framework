package com.github.bali.auth.provider.listener;

import cn.hutool.core.util.StrUtil;
import com.github.bali.auth.domain.dto.LoginAttempts;
import com.github.bali.auth.exception.AuthenticationFailureException;
import com.github.bali.auth.properties.AuthorizeProperties;
import com.github.bali.auth.service.IUserService;
import com.github.bali.core.framework.utils.ElapsedTimeStringUtil;
import com.github.bali.security.constants.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;

/**
 * @author Pettyfer
 */
@Slf4j
@Component
public class AuthenticationFailureLockedListener implements ApplicationListener<AuthenticationFailureLockedEvent> {

    private final AuthorizeProperties properties;

    private final IUserService userService;

    public AuthenticationFailureLockedListener(AuthorizeProperties properties, IUserService userService) {
        this.properties = properties;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureLockedEvent e) {
        Authentication authentication = e.getAuthentication();
        if (authentication.getPrincipal() instanceof String) {
            String username = (String) authentication.getPrincipal();
            if(authentication.getDetails() instanceof WebAuthenticationDetails){
                String message = processEvent(username);
                if (StrUtil.isNotEmpty(message)) {
                    throw new AuthenticationFailureException(message);
                }
            } else if (authentication.getDetails() instanceof LinkedHashMap) {
                LinkedHashMap<String, String> details = (LinkedHashMap<String, String>) authentication.getDetails();
                if (details.containsKey(SecurityConstant.GRANT_TYPE)) {
                    String grantType = details.get(SecurityConstant.GRANT_TYPE);
                    if (properties.getLoginAttempt().getGranters().contains(grantType)) {
                        String message = processEvent(username);
                        if (StrUtil.isNotEmpty(message)) {
                            throw new AuthenticationFailureException(message);
                        }
                    }
                }
            }
        }
    }

    private String processEvent(String username) {
        if (properties.getLoginAttempt().getEnable()) {
            LoginAttempts loginAttempts = userService.getLoginAttempts(username);
            if (loginAttempts != null) {
                AuthorizeProperties.LoginAttempt loginAttemptConfig = properties.getLoginAttempt();
                LocalDateTime lockingTimeEnd = loginAttempts.getLastLoginFailTime().plus(loginAttemptConfig.getLockingDuration(), ChronoUnit.MILLIS);
                String lockTime = ElapsedTimeStringUtil.elapsed(lockingTimeEnd.toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000);
                return "帐号或密码错误，您的账号登录尝试过多，已被锁定。（约" + lockTime + "）";
            }
        }
        return null;
    }

}
