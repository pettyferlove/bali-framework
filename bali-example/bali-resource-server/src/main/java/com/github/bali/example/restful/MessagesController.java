package com.github.bali.example.restful;

import com.github.bali.core.framework.constants.ApiConstant;
import com.github.bali.security.userdetails.BaliUserDetails;
import com.github.bali.security.utils.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pettyfer
 */
@RestController
@RequestMapping(ApiConstant.API_V1_PREFIX)
public class MessagesController {

    @PreAuthorize("hasScope('aa')")
    @GetMapping("/messages/info")
    public String[] getMessages() {
        BaliUserDetails user = SecurityUtil.getUser();
        System.out.println(user);
        return new String[]{"Message 1", "Message 2", "Message 3"};
    }
}