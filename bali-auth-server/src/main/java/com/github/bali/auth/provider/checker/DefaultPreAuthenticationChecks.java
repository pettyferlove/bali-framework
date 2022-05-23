package com.github.bali.auth.provider.checker;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * @author Pettyfer
 */
public class DefaultPreAuthenticationChecks implements UserDetailsChecker {

    protected MessageSourceAccessor messages;

    public DefaultPreAuthenticationChecks() {
        ReloadableResourceBundleMessageSource localMessageSource = new ReloadableResourceBundleMessageSource();
        localMessageSource.setBasenames("messages_zh_CN");
        messages = new MessageSourceAccessor(localMessageSource);
    }

    @Override
    public void check(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            throw new LockedException(messages
                    .getMessage("DefaultPreAuthenticationChecks.locked", "User account is locked"));
        }
        if (!user.isEnabled()) {
            throw new DisabledException(messages
                    .getMessage("DefaultPreAuthenticationChecks.disabled", "User is disabled"));
        }
        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException(messages
                    .getMessage("DefaultPreAuthenticationChecks.expired", "User account has expired"));
        }
    }

}
