package com.px.myshiro.repository;

import com.px.myshiro.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * User: gxd
 * Date: 2019/7/18
 * Time: 11:40
 * Version:V1.0
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
    Set<Role> getRolesById(Long name);
}
