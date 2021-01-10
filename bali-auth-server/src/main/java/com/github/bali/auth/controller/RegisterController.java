package com.github.bali.auth.controller;

import com.github.bali.auth.domain.dto.ClientDTO;
import com.github.bali.auth.utils.BasicAuthorizationUtil;
import com.github.bali.core.framework.domain.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@RestController
@RequestMapping("/register")
@Api(tags = {"用户注册接口"})
public class RegisterController {

    @PostMapping("web")
    @ApiOperation(value = "Web用户注册", notes = "需客户端Basic认证")
    public void web(@RequestHeader HttpHeaders headers) {
        ClientDTO client = BasicAuthorizationUtil.authenticate(headers);
    }

    @PostMapping("mobile")
    @ApiOperation(value = "移动用户注册", notes = "需客户端Basic认证")
    public R<Boolean> mobile() {
        return null;
    }

    @PostMapping("wechat-mini-program")
    @ApiOperation(value = "小程序用户注册", notes = "需客户端Basic认证")
    public R<Boolean> wechatMiniProgram() {
        return null;
    }

}
