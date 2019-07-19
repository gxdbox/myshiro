package com.px.myshiro.shiro;

import com.px.myshiro.domain.User;
import com.px.myshiro.service.RoleService;
import com.px.myshiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * User: gxd
 * Date: 2019/7/14
 * Time: 7:24
 * Version:V1.0
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 授权查询回调函数，进行授权但缓存中无用户的授权时调用
     *
     *
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        User user = (User)principal.getPrimaryPrincipal();
        Long idd = Long.valueOf(user.getId());

        Long id =user.getId();
        Set<String> roles = roleService.getRolesById(id);
        //Set<String> roles = new HashSet<>();
        //roles.add("admin");
        //roles.add("user");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);

        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user = userService.getUserByUsername(token.getUsername());

        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        if (user != null) {
            try {
                simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
                simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getUsername()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return simpleAuthenticationInfo;
        } else {
            return null;
        }
    }


    public static void main(String[] args) {
        Md5Hash admin = new Md5Hash("123456", "admin");
        System.out.println(admin);
    }
}
