package com.github.bali.auth.controller;

import com.github.bali.core.framework.domain.vo.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@RestController
@RequestMapping("/register")
@Api(value = "用户注册接口", tags = {"用户信息", "注册"})
public class RegisterController {

    @PostMapping("web")
    public void web(){
        System.out.println("success");
    }

    @PostMapping("mobile")
    public R<Boolean> mobile() {
        return null;
    }

}
