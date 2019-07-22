package com.px.myshiro.controller;

import com.px.myshiro.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: gxd
 * Date: 2019/7/12
 * Time: 11:01
 * Version:V1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    @ResponseBody
    public String login(User user){
        //获取主体
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            token.setRememberMe(user.isRememberMe());
            subject.login(token);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }

        if (subject.hasRole("user")){
            return "有user权限";
        }else {
            return "没有权限";
        }
    }



    @RequestMapping("/testRole")
    @ResponseBody
    public String testRole(){
        return "testRole";
    }

    @RequiresRoles("admin")
    @RequestMapping("/testRole1")
    @ResponseBody
    public String testRole1(){
        System.out.println("testRole1");
        int i= 1;
        int a = 3;
        return "testRole1";
    }


    /**
     * 登录失败，真正登录的POST请求由Filter完成
     * @param request
     * @return
     */
/*    @RequiresPermissions("user")
    @PostMapping("/failLogin")
    public String login(HttpServletRequest request , User user , Model model){
        CustomRealm.Principal principal = UserUtils.getPrincipal();

        // 如果已经登录，则跳转到管理首页
        if(principal != null){
            return "redirect:" + "/";
        }

        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        String message = (String)request.getAttribute("message");

        if (StringUtils.isBlank(message) || StringUtils.equals(message, null)){
            message = "用户或密码错误, 请重试.";
        }
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute("message", message);
        model.addAttribute("loginError", true);
        return "login";
    }*/

}
