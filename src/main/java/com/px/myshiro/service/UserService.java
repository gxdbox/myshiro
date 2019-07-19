package com.px.myshiro.service;

import com.px.myshiro.domain.User;

/**
 * User: gxd
 * Date: 2019/7/16
 * Time: 16:51
 * Version:V1.0
 */
public interface UserService {
    User getUserByUsername(String username);



    //Set<String> getPermissionsByUsername(String username);
}
