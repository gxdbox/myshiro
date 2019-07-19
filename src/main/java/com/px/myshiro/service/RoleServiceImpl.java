package com.px.myshiro.service;

import com.px.myshiro.domain.Role;
import com.px.myshiro.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * User: gxd
 * Date: 2019/7/18
 * Time: 11:38
 * Version:V1.0
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Set<String> getRolesById(Long name) {
        Set<Role> roles=roleRepository.getRolesById(name);
        Set<String> sets = new HashSet<>();
        for (Role role : roles) {
            sets.add(role.getName());
        }
        return sets;
    }
}
