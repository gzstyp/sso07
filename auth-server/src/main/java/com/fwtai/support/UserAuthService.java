package com.fwtai.support;

import com.fwtai.domain.SysPermission;
import com.fwtai.domain.SysRole;
import com.fwtai.domain.SysUser;
import com.fwtai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 授权的时候是对角色授权，而认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
    */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final SysUser sysUser = userService.loginAuth(username);
        if (null == sysUser) {
            throw new UsernameNotFoundException(username);
        }
        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (final SysRole role : sysUser.getRoleList()) {
            for (final SysPermission permission : role.getPermissionList()) {
                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
            }
        }
        return new User(sysUser.getUsername(),sysUser.getPassword(),authorities);
    }
}