package com.github.bali.example.restful;

import com.github.bali.security.userdetails.BaliUserDetails;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pettyfer
 */
@RestController
public class MessagesController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/messages/info")
    public String[] getMessages() {
        BaliUserDetails user = SecurityUtil.getUser();
        System.out.println(user);
        return new String[]{"Message 1", "Message 2", "Message 3"};
    }
}