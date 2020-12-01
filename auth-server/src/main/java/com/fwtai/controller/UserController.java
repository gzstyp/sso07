package com.fwtai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    //认证成功后获取登录者的信息,这个方便各个子系统获取登录信息
    @GetMapping("/info")
    public Principal user(final Principal principal) {
        System.out.println(principal);
        return principal;
    }
}