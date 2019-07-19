package com.px.myshiro.config;

import com.px.myshiro.sessionDao.CustomSessionManage;
import com.px.myshiro.sessionDao.SessionDao;
import com.px.myshiro.shiro.CustomFilter;
import com.px.myshiro.shiro.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * User: gxd
 * Date: 2019/7/12
 * Time: 13:24
 * Version:V1.0
 *
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //获取filters
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();

        //将自定义 的CustomFilter注入shiroFilter中
/*        LinkedHashMap<String, Filter> filtermap = new LinkedHashMap<>();
        filtermap.put("myfilter",customFilter());
        shiroFilterFactoryBean.setFilters(filtermap);*/
        shiroFilterFactoryBean.setFilters(filters);


        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 登录成功后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/");

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        /**
         * 配置shiro拦截器链
         *
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         *
         */
        //filterChainDefinitionMap.put("login.html","anon");
        //filterChainDefinitionMap.put("user/login","anon");
        //filterChainDefinitionMap.put("myfilter","user");
        filterChainDefinitionMap.put("/tst/login","anon");
        filterChainDefinitionMap.put("/user/login","anon");
        //配置记住我或认证通过可以访问的地址
        //filterChainDefinitionMap.put("/", "user");
        //filterChainDefinitionMap.put("/login", "authc");

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //filterChainDefinitionMap.put("/logout", "logout");

        //所有url都必须有user权限才可以访问 一般讲/**放在最后面
        //filterChainDefinitionMap.put("/**", "myfilter[admin,user]");
        filterChainDefinitionMap.put("/**", "user");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.html"页面
        //shiroFilterFactoryBean.setLoginUrl("/user/login");
        //shiroFilterFactoryBean.setUnauthorizedUrl("tst/unauth");
        //shiroFilterFactoryBean.setSuccessUrl("/tst/success");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("customRealm") CustomRealm customRealm,
                                           @Qualifier("sessionManager") SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean(name = "customRealm")
    public CustomRealm customRealm(HashedCredentialsMatcher matcher){
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(matcher);
        return customRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashIterations(1);
        matcher.setHashAlgorithmName("md5");
        return matcher;
    }

    @Bean(name = "sessionManager")
    public SessionManager sessionManager(SessionDao sessionDao){
        CustomSessionManage customSessionManage = new CustomSessionManage();
        customSessionManage.setSessionDAO(sessionDao);
        return customSessionManage;
    }

    @Bean
    public JedisPool jedisPool(){
        JedisPool jedisPool = new JedisPool();
        return jedisPool;
    }

    /**
     * 开启shiro注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager")SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public CustomFilter customFilter(){
        return new CustomFilter();
    }

}
