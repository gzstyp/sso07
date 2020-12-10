package com.fwtai.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    /**
     * 会员列表页面,http://192.168.3.108:8082/coupon/list
    */
    @RequestMapping("/list")
    public ModelAndView list(final Principal pl){
        System.out.println(pl);
        final SecurityContext context = SecurityContextHolder.getContext();//获取当前登录账号信息
        System.out.println(context);
        final Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
        } else {
            String username = principal.toString();
        }
        return new ModelAndView("coupon/list");
    }
}