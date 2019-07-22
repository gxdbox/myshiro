package com.px.myshiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: gxd
 * Date: 2019/7/12
 * Time: 14:41
 * Version:V1.0
 */
@Controller
@RequestMapping("/tst")
public class TestController {
    /**
     *
     * @param user(username 用户名：password密码)
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        System.out.println("测试login");
        return "login";
    }

    @GetMapping("/success")
    public String success(){
        System.out.println("测试success");
        return "success";
    }

    @GetMapping("/unauth")
    public String unauth(){
        System.out.println("测试unauth");
        return "403";
    }
}
