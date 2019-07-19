package com.px.myshiro.shiro;

import com.px.myshiro.util.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * User: gxd
 * Date: 2019/7/16
 * Time: 17:53
 * Version:V1.0
 */
public class CustomFormAuthticationFilter extends FormAuthenticationFilter {

    public static final String DEFAULT_MESSAGE_PARAM = "message";

    private String messageParam = DEFAULT_MESSAGE_PARAM;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        return super.createToken(request, response);
    }

    /**
     * 获取用户名
     * @param request
     * @return
     */
    @Override
    protected String getUsername(ServletRequest request) {
        return super.getUsername(request);
    }

    /**
     *登录之后跳转url
     * @return
     */
    @Override
    public String getSuccessUrl() {
        return super.getSuccessUrl();
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String className = e.getClass().getName(), message = "";
        if (IncorrectCredentialsException.class.getName().equals(className)
                || UnknownAccountException.class.getName().equals(className)){
            message = "用户或密码错误, 请重试.";
        }
        else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
            message = StringUtils.replace(e.getMessage(), "msg:", "");
        }
        else{
            message = "系统出现点问题，请稍后再试！";
            e.printStackTrace(); // 输出到控制台
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
    }

    private String getMessageParam() {
        return messageParam;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return super.onLoginSuccess(token, subject, request, response);
    }
}
