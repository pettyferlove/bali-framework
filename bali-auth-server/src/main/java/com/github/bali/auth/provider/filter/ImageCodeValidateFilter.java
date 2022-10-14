package com.github.bali.auth.provider.filter;

import cn.hutool.core.util.StrUtil;
import com.github.bali.auth.provider.service.ICaptchaValidateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Pettyfer
 */
@Slf4j
@Component
@AllArgsConstructor
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    private final ICaptchaValidateService captchaValidateService;

    private final SimpleUrlAuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler("/login?error");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/authorize"
                .equals(request.getRequestURI())
                && request.getMethod().equalsIgnoreCase("post")) {
            try {
                // 校验验证码合法性
                String machineCode = request.getParameter("machine_code");
                String captcha = request.getParameter("captcha");
                if (StrUtil.isBlank(machineCode) || StrUtil.isBlank(captcha)) {
                    throw new BadCredentialsException("验证码不能为空");
                }
                if (!captchaValidateService.validate(machineCode, captcha)) {
                    throw new BadCredentialsException("验证码错误");
                }
            } catch (AuthenticationException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
