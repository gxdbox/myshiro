package com.px.myshiro.service;

import com.px.myshiro.domain.User;
import com.px.myshiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: gxd
 * Date: 2019/7/16
 * Time: 16:54
 * Version:V1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        User user=userRepository.getUserByUsername(username);
        return user;
    }



}
