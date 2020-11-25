package com.fwtai.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(final WebSecurity web) throws Exception{
        web.ignoring()//忽略
            .antMatchers("/bootstrap/**","/**.ico","/error");
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/", "/login**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .successHandler((request,response,authentication) ->{
                response.setContentType("text/html;charset=utf-8");
                response.setHeader("Cache-Control","no-cache");
                response.getWriter().write("登录成功");
            });

    }
}