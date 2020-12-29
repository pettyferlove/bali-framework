package com.github.bali.example.restful;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pettyfer
 */
@RestController
public class MessagesController {

    @GetMapping("/messages")
    public String[] getMessages(Authentication authentication) {
        System.out.println(authentication);
        return new String[] {"Message 1", "Message 2", "Message 3"};
    }
}