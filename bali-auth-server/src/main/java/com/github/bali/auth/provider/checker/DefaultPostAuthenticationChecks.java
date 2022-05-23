package com.github.bali.auth.provider.checker;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * @author Pettyfer
 */
public class DefaultPostAuthenticationChecks implements UserDetailsChecker {

    protected MessageSourceAccessor messages;

    public DefaultPostAuthenticationChecks() {
        ReloadableResourceBundleMessageSource localMessageSource = new ReloadableResourceBundleMessageSource();
        localMessageSource.setBasenames("messages_zh_CN");
        messages = new MessageSourceAccessor(localMessageSource);
    }

    @Override
    public void check(UserDetails user) {
        if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(messages
                    .getMessage("DefaultPostAuthenticationChecks.credentialsExpired",
                            "User credentials have expired"));
        }
    }

}
