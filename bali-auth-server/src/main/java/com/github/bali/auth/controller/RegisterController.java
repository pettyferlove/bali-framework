package com.github.bali.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @PostMapping("user")
    public void user(){
        System.out.println("success");
    }

}
