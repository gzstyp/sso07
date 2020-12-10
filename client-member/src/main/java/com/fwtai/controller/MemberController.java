package com.fwtai.controller;

import com.fwtai.domain.Member;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    /**
     * 会员列表页面,http://192.168.3.108:8081/member/list
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
        final ModelAndView modelAndView = new ModelAndView("member/list");
        return modelAndView;
    }

    /**
     * 导出
    */
    @PreAuthorize("hasAuthority('memberExport')")
    @ResponseBody
    @RequestMapping("/export")
    public List<Member> export() {
        Member member = new Member();
        member.setName("苏九儿");
        member.setCode("1000");
        member.setMobile("13112345678");
        member.setGender(1);
        Member member1 = new Member();
        member1.setName("郭双");
        member1.setCode("1001");
        member1.setMobile("15812346723");
        member1.setGender(1);
        List<Member> list = new ArrayList<>();
        list.add(member);
        list.add(member1);
        return list;
    }

    /**
     * 详情
    */
    @PreAuthorize("hasAuthority('memberDetail')")
    @RequestMapping("/detail")
    public ModelAndView detail() {
        return new ModelAndView(" member/detail");
    }
}