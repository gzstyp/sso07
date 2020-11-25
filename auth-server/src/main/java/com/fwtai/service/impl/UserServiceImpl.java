package com.fwtai.service.impl;

import com.fwtai.dao.UserDao;
import com.fwtai.domain.SysUser;
import com.fwtai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public SysUser loginAuth(final String username){
        return userDao.loginAuth(username);
    }
}