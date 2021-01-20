package com.github.bali.auth.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.bali.auth.domain.dto.BasicAuth;
import com.github.bali.auth.domain.vo.WeChatUserRegister;
import com.github.bali.auth.domain.vo.WebUserRegister;
import com.github.bali.auth.entity.AuthClientDetails;
import com.github.bali.auth.service.IAuthClientDetailsService;
import com.github.bali.auth.service.IRegisterService;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import com.github.bali.security.constants.UserChannelType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Petty
 */
@RestController
@RequestMapping("/register")
@Api(tags = {"用户注册接口"})
public class RegisterController {

    private final IAuthClientDetailsService authClientDetailsService;

    private final IRegisterService registerService;

    private final PasswordEncoder passwordEncoder;

    public RegisterController(IAuthClientDetailsService authClientDetailsService, IRegisterService registerService, PasswordEncoder passwordEncoder) {
        this.authClientDetailsService = authClientDetailsService;
        this.registerService = registerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("web")
    @ApiOperation(value = "Web用户注册", notes = "需客户端Basic认证")
    public R<Boolean> web(WebUserRegister register, @RequestHeader HttpHeaders headers) {
        BasicAuth authenticate = authenticate(headers);
        throw new BaseRuntimeException("暂不支持Web端用户注册，请联系管理员添加用户");
    }

    @PostMapping("mobile")
    @ApiOperation(value = "移动用户注册", notes = "需客户端Basic认证")
    public R<Boolean> mobile(@RequestHeader HttpHeaders headers) {
        BasicAuth authenticate = authenticate(headers);
        return null;
    }

    @PostMapping("wechat-mini-program")
    @ApiOperation(value = "小程序用户注册", notes = "需客户端Basic认证")
    public R<Boolean> wechatMiniProgram(WeChatUserRegister register, @RequestHeader HttpHeaders headers) {
        BasicAuth authenticate = authenticate(headers);
        return new R<>(registerService.registerWeChat(register, authenticate, UserChannelType.WECHAT_MINI_PROGRAM));
    }

    private BasicAuth authenticate(HttpHeaders headers) {
        List<String> authorization = Optional.ofNullable(headers.get("Authorization")).orElseGet(ArrayList::new);
        String basicAuthorization = "";
        if (!authorization.isEmpty()) {
            basicAuthorization = authorization.get(0);
        }
        String basic = basicAuthorization.replace("Basic ", "");
        String decode = Base64.decodeStr(basic);
        String[] split = decode.split(":");
        if (split.length == 2) {
            String clientId = split[0];
            String clientSecret = split[1];
            AuthClientDetails authClientDetails = authClientDetailsService.getOne(Wrappers.<AuthClientDetails>lambdaQuery().eq(AuthClientDetails::getClientId, clientId));
            if (ObjectUtil.isNotNull(authClientDetails)) {
                if (passwordEncoder.matches(clientSecret, authClientDetails.getClientSecret())) {
                    return BasicAuth.builder().tenantId(authClientDetails.getTenantId()).clientId(clientId).build();
                } else {
                    throw new BaseRuntimeException("client authentication error", HttpStatus.UNAUTHORIZED);
                }
            } else {
                throw new BaseRuntimeException("the client is not registered", HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new BaseRuntimeException("client information error", HttpStatus.UNAUTHORIZED);
        }
    }

}
