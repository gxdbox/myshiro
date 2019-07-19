package com.px.myshiro.service;

import java.util.Set;

/**
 * User: gxd
 * Date: 2019/7/18
 * Time: 11:37
 * Version:V1.0
 */
public interface RoleService {
    Set<String> getRolesById(Long name);
}
