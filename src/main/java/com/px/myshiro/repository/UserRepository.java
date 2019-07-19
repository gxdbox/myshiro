package com.px.myshiro.repository;

import com.px.myshiro.domain.Role;
import com.px.myshiro.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * User: gxd
 * Date: 2019/7/16
 * Time: 16:56
 * Version:V1.0
 */
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    Set<Role> getRolesByUsername(String username);
}
