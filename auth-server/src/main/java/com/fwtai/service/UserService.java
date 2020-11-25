package com.fwtai.service;


import com.fwtai.domain.SysUser;

public interface UserService {

    /**
     * 根据用户名获取系统用户
    */
    SysUser loginAuth(final String username);

}
