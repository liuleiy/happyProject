package com.happyProject.admin.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class ShiroConfig {

	// 将自己的验证方式加入容器
	@Bean
	public ShiroRealm myShiroRealm() {
		ShiroRealm myShiroRealm = new ShiroRealm();
		return myShiroRealm;
	}

	@Bean
	public SecurityManager securityManager() {
		// 注意是DefaultWebSecurityManager！！！
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

//	@Bean
//	public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//		return new DefaultAdvisorAutoProxyCreator();
//	}
	@Bean
	public SessionManager sessionManager() {
		MySessionManager mySessionManager = new MySessionManager();
		return mySessionManager;
	}

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 注意过滤器配置顺序 不能颠倒
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
		filterChainDefinitionMap.put("/logout", "logout");
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/ajaxLogin", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/unauth", "anon");
		filterChainDefinitionMap.put("/goods/shiyan", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		// 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
		shiroFilterFactoryBean.setLoginUrl("/unauth");
		// 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	// 开启注解
//	保证实现了Shiro内部lifecycle函数的bean执行
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
