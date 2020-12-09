package com.fwtai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/*
    0、访问请求 http://192.168.3.108:8082/coupon/list 或 http://192.168.3.108:8081/member/list
    1、去登录认证中心登录
    2、通过去登录认证中心返回code
    3、通过认证服务器端返回code去访问本子系统验证登录,验证接口 /login
    4、访问之前的请求url http://192.168.3.108:8082/coupon/list
*/
@Configuration
@EnableAuthorizationServer//认证服务器
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private DataSource dataSource;//基于数据库

    /**
     * 配置授权服务器的安全，意味着实际上是/oauth/token端点。
     * /oauth/authorize端点也应该是安全的
     * 默认的设置覆盖到了绝大多数需求，所以一般情况下你不需要做任何事情。
    */
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    /**
     * 配置ClientDetailsService
     * 注意，除非你在下面的configure(AuthorizationServerEndpointsConfigurer)中指定了一个AuthenticationManager，否则密码授权方式不可用。
     * 至少配置一个client，否则服务器将不会启动。
    */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        /*

            CREATE TABLE `oauth_client_details`  (
              `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
              `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
              `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
              `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
              `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
              `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
              `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
              `access_token_validity` int(0) NULL DEFAULT NULL,
              `refresh_token_validity` int(0) NULL DEFAULT NULL,
              `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
              `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
              PRIMARY KEY (`client_id`) USING BTREE
            ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

            INSERT INTO `oauth_client_details` VALUES ('MemberSystem', NULL, '$2a$10$dYRcFip80f0jIKGzRGulFelK12036xWQKgajanfxT65QB4htsEXNK', 'user_info', 'authorization_code,refresh_token', 'http://localhost:8081/login', NULL, NULL, NULL, NULL, 'user_info');
            INSERT INTO `oauth_client_details` VALUES ('CouponSystem', NULL, '$2a$10$dYRcFip80f0jIKGzRGulFelK12036xWQKgajanfxT65QB4htsEXNK', 'user_info', 'authorization_code,refresh_token', 'http://localhost:8082/login', NULL, NULL, NULL, NULL, 'user_info');
        */
        //clients.jdbc(dataSource);//基于数据库

        // 定义了两个客户端应用的通行证
        clients.inMemory()
            .withClient("MemberSystem")//会员子系统名称,对应的子系统配置是 security.oauth2.client.client-id=CouponSystem
            .secret(new BCryptPasswordEncoder().encode("12345"))
            .authorizedGrantTypes("authorization_code","refresh_token","password")
            .scopes("all")
            .autoApprove(true)//自动授权,即免去鼠标去点击授权按钮
            //加上验证后回调地址???若是客户端不填写的话,那就是事先访问的url认证后跳转到原访问的URL
            .redirectUris("http://192.168.3.108:8081/login")//member会员子系统登录;去登录认证成功后返回code去访问本子系统验证登录接口url
            .and()//第2个子系统
            .withClient("CouponSystem")//优惠卷子系统名称,对应的子系统配置是 security.oauth2.client.client-id=CouponSystem
            .secret(new BCryptPasswordEncoder().encode("12345"))
            .authorizedGrantTypes("authorization_code","refresh_token","password")
            .scopes("all")
            .autoApprove(true)//自动授权,即免去鼠标去点击授权按钮
            .redirectUris("http://192.168.3.108:8082/login");//coupon优惠卷子系统登录;去登录认证成功后返回code去访问本子系统验证登录接口url
    }

    /**
     * 该方法是用来配置Authorization Server endpoints的一些非安全特性的，比如token存储、token自定义、授权类型等等的
     * 默认情况下，你不需要做任何事情，除非你需要密码授权，那么在这种情况下你需要提供一个AuthenticationManager
    */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
    }
}