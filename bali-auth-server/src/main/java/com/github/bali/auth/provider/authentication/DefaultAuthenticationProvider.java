package com.github.bali.auth.provider.authentication;

import cn.hutool.crypto.SecureUtil;
import com.github.bali.auth.properties.AuthorizeProperties;
import com.github.bali.auth.provider.service.OAuth2UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * @author Pettyfer
 */
@Component
public class DefaultAuthenticationProvider extends DaoAuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    private final AuthorizeProperties properties;

    public DefaultAuthenticationProvider(PasswordEncoder passwordEncoder, OAuth2UserDetailsService userDetailsService, AuthorizeProperties properties) {
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
        super.setPasswordEncoder(passwordEncoder);
        super.setUserDetailsService(userDetailsService);
    }

    @PostConstruct
    public void initProvider() {
        ReloadableResourceBundleMessageSource localMessageSource = new ReloadableResourceBundleMessageSource();
        localMessageSource.setBasenames("messages_zh_CN");
        messages = new MessageSourceAccessor(localMessageSource);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String password = authentication.getCredentials().toString();
        if(properties.getEnablePasswordEncrypt()) {
            try {
                password = SecureUtil.aes(properties.getEncryptKey().getBytes(StandardCharsets.UTF_8)).decryptStr(password);
            } catch (Exception e) {
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
        if (!this.passwordEncoder.matches(password, userDetails.getPassword())) {
            this.logger.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return super.authenticate(authentication);
    }
}
