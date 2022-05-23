package com.github.bali.auth.provider.authentication;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.bali.auth.properties.AuthorizeProperties;
import com.github.bali.auth.provider.checker.DefaultPostAuthenticationChecks;
import com.github.bali.auth.provider.checker.DefaultPreAuthenticationChecks;
import com.github.bali.auth.provider.service.ICaptchaValidateService;
import com.github.bali.auth.provider.service.OAuth2UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pettyfer
 */
@Slf4j
@Component
public class CaptchaUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();

    private final UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();
    private final AuthorizeProperties properties;
    protected MessageSourceAccessor messages;
    @PostConstruct
    public void initProvider() {
        ReloadableResourceBundleMessageSource localMessageSource = new ReloadableResourceBundleMessageSource();
        localMessageSource.setBasenames("messages_zh_CN");
        messages = new MessageSourceAccessor(localMessageSource);
    }
    private final PasswordEncoder passwordEncoder;

    private final OAuth2UserDetailsService userDetailsService;

    private final ICaptchaValidateService captchaValidateService;

    public CaptchaUsernamePasswordAuthenticationProvider(AuthorizeProperties properties, PasswordEncoder passwordEncoder, OAuth2UserDetailsService userDetailsService, ICaptchaValidateService captchaValidateService) {
        this.properties = properties;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.captchaValidateService = captchaValidateService;
    }

    private void additionalAuthenticationChecks(UserDetails userDetails, CaptchaUsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String password = authentication.getCredentials().toString();
        if (properties.getEnablePasswordEncrypt()) {
            try {
                password = SecureUtil.aes(properties.getEncryptKey().getBytes(StandardCharsets.UTF_8)).decryptStr(password);
            } catch (Exception e) {
                throw new BadCredentialsException(this.messages.getMessage("CaptchaUsernamePasswordAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("CaptchaUsernamePasswordAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Map<String, Object> details = (HashMap<String, Object>) authentication.getDetails();
        Map<String, String> principal = (HashMap<String, String>) authentication.getPrincipal();
        String username = principal.get("username");
        String captcha = principal.get("captcha");
        String machineCode = principal.get("machine_code");
        String password = (String) authentication.getCredentials();
        if (StrUtil.isBlank(password)) {
            throw new BadCredentialsException(this.messages.getMessage("CaptchaUsernamePasswordAuthenticationProvider.emptyPassword", "The password cannot be empty"));
        }
        if (StrUtil.isBlank(captcha)) {
            throw new BadCredentialsException(this.messages.getMessage("CaptchaUsernamePasswordAuthenticationProvider.emptyCaptcha", "The captcha cannot be empty"));
        }
        if(!captchaValidateService.validate(machineCode, captcha)){
            throw new BadCredentialsException(this.messages.getMessage("CaptchaUsernamePasswordAuthenticationProvider.wrongCaptcha", "Invalid or wrong verification code"));
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        this.preAuthenticationChecks.check(userDetails);
        additionalAuthenticationChecks(userDetails, (CaptchaUsernamePasswordAuthenticationToken) authentication);
        this.postAuthenticationChecks.check(userDetails);
        return new CaptchaUsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (CaptchaUsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

}
