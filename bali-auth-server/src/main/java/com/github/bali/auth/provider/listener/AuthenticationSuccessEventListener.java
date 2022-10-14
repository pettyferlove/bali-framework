package com.github.bali.auth.provider.listener;

import com.github.bali.auth.properties.AuthorizeProperties;
import com.github.bali.auth.service.IUserService;
import com.github.bali.security.userdetails.BaliUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author Pettyfer
 */
@Slf4j
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final AuthorizeProperties properties;

    private final IUserService userService;

    public AuthenticationSuccessEventListener(AuthorizeProperties properties, IUserService userService) {
        this.properties = properties;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        Authentication authentication = e.getAuthentication();
        if (authentication.getPrincipal() instanceof BaliUserDetails) {
            BaliUserDetails userDetails = (BaliUserDetails) authentication.getPrincipal();
            if (authentication.getDetails() instanceof WebAuthenticationDetails) {
                processEvent(userDetails);
            } else if (authentication.getDetails() instanceof LinkedHashMap) {
                LinkedHashMap<String, String> details = (LinkedHashMap<String, String>) authentication.getDetails();
                if (details.containsKey("grant_type")) {
                    String grantType = details.get("grant_type");
                    if (properties.getLoginAttempt().getGranters().contains(grantType)) {
                        processEvent(userDetails);
                    }
                }
            }
        }
    }

    private void processEvent(BaliUserDetails userDetails) {
        if (properties.getLoginAttempt().getEnable()) {
            if (userDetails.isAccountNonLocked()) {
                userService.loginSucceeded(userDetails.getUsername());
            }
        }
    }

}
