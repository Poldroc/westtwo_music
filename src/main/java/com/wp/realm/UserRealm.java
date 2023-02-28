package com.wp.realm;

import com.wp.domain.User;
import com.wp.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("认证doGetAuthorizationInfo...");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        User user = userService.queryUserByName(userToken.getUsername());



        System.out.println("___________"+user);
        System.out.println("___________"+token);

        if (user == null) {//没有这个人
            return null;
        }

        return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),getName());


    }
}
