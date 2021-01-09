package com.github.bali.auth.web;

import com.github.bali.auth.service.IClientDetailsService;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Petty
 */
@RestController
public class IndexController {

    private final IClientDetailsService clientDetailsService;

    public IndexController(IClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @GetMapping("user")
    public Object user() {
        return SecurityUtil.getAuthentication().getPrincipal();
    }

    @GetMapping("client")
    public Object client(LocalDateTime dateTime) {
        System.out.println(dateTime);
        return clientDetailsService.list();
    }

}
