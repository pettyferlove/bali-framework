package com.github.bali.example.restful;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Pettyfer
 */
@RestController
public class UserController {

    @GetMapping("/oauth/user")
    public JwtAuthenticationToken user(JwtAuthenticationToken token) {
        return token;
    }
}
