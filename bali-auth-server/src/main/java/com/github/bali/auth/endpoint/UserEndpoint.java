package com.github.bali.auth.endpoint;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author Pettyfer
 */
@FrameworkEndpoint
public class UserEndpoint {

    @RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Principal user(Principal principal) {
        return principal;
    }

}
