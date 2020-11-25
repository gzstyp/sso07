package com.fwtai.dao;

import com.fwtai.domain.SysPermission;
import com.fwtai.domain.SysRole;
import com.fwtai.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Repository
public class UserDao {

    private SysRole admin = new SysRole("ADMIN","管理员");
    private SysRole developer = new SysRole("DEVELOPER","开发者");

    {
        final SysPermission p1 = new SysPermission();
        p1.setCode("memberExport");
        p1.setName("会员列表导出");
        p1.setUrl("/member/export");

        final SysPermission p2 = new SysPermission();
        p2.setCode("BookList");
        p2.setName("图书列表");
        p2.setUrl("/book/list");
        admin.setPermissionList(Arrays.asList(p1,p2));
        developer.setPermissionList(Arrays.asList(p1));

    }

    // 登录并授权
    public SysUser loginAuth(final String username) {
        log.info("模拟从数据库中查询用户");
        if ("zhangsan".equals(username)){
            SysUser sysUser = new SysUser("zhangsan","$2a$10$aZDOWYEvK06TrxN6g0Mta.X3gtnj1sHPReRic5YRcOiXl4yMctwS6");//123456
            sysUser.setRoleList(Arrays.asList(admin,developer));//多个角色
            return sysUser;
        }else if ("lisi".equals(username)) {
            SysUser sysUser = new SysUser("lisi","$2a$10$aZDOWYEvK06TrxN6g0Mta.X3gtnj1sHPReRic5YRcOiXl4yMctwS6");//123456
            sysUser.setRoleList(Collections.singletonList(developer));//单个角色
            return sysUser;
        }
        return null;
    }
}