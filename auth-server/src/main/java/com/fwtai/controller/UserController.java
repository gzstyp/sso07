package com.fwtai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    //认证成功后获取登录者的信息
    @RequestMapping("/user/me")
    public Principal user(final Principal principal) {
        System.out.println(principal);
        return principal;
    }
}