package com.happyProject.admin.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyProject.admin.model.Condition;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.service.impl.TUserServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.Md5Util;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@CrossOrigin
@RequestMapping
public class UserController {
	@Resource
	private TUserServiceImpl tUserServiceImpl;
	
	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;
	
	
	// @RequestParam 参数
	// @PathVariable 映射url片段到java方法的参数
	@PostMapping("/login")
	public ModelMap login(@RequestBody Condition condition,HttpServletRequest request) {
		String username = condition.getUsername();
		String password2 = condition.getPassword();
		String password = Md5Util.getMd5String(password2).toLowerCase();
		ModelMap modelMap = new ModelMap();
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()) {//有问题
			UsernamePasswordToken token = new UsernamePasswordToken(username,
					password);
			try {
				
				subject.login(token);//realm方法验证登录
				//String sessionId = (String) subject.getSession().getId();
				//System.out.println("sessionId:"+sessionId);
				//modelMap.put("sessionId",sessionId);
				//System.out.println("发送请求");
				//modelMap.put("state", 0);
				
				TUser tUser = tUserServiceImpl.login(username, password);
				if (tUser.getStatus() == 1) {
					SecurityUtils.getSubject().logout();
					modelMap.put("state", -2);
					
					OperationLog ot = new OperationLog(null, tUser.getUser_name(), IpAddrs.getIpAddr(request),
							"账户禁用,登录失败", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
					operationLogServiceImpl.AddAnaUpdate(ot);
				}else {
					modelMap.put("state", 0);
					
					HttpSession session = request.getSession();
					session.setAttribute("tUser", tUser);
					
					// 日志
					OperationLog ot = new OperationLog(null, tUser.getUser_name(), IpAddrs.getIpAddr(request),
							"登录", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
					operationLogServiceImpl.AddAnaUpdate(ot);
				}
				
				
			}catch (UnknownAccountException e) {
				
				modelMap.put("state", 1);
			}
		}
		if(!subject.isAuthenticated()) {//是否验证,需要验证
			UsernamePasswordToken token = new UsernamePasswordToken(username,
					password);
			try {
				
				subject.login(token);//realm方法验证登录
				modelMap.put("state", 0);
				TUser tUser = tUserServiceImpl.login(username, password);
				if (tUser.getStatus() == 1) {
					SecurityUtils.getSubject().logout();
					modelMap.put("state", -2);
					
					OperationLog ot = new OperationLog(null, tUser.getUser_name(), IpAddrs.getIpAddr(request),
							"账户禁用,登录失败", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
					operationLogServiceImpl.AddAnaUpdate(ot);
				}else {
					modelMap.put("state", 0);
					
					HttpSession session = request.getSession();
					session.setAttribute("tUser", tUser);
					
					// 日志
					OperationLog ot = new OperationLog(null, tUser.getUser_name(), IpAddrs.getIpAddr(request),
							"登录", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
					operationLogServiceImpl.AddAnaUpdate(ot);
				}
				
			}catch (UnknownAccountException e) {
				
				modelMap.put("state", 1);
			}
			
		}
		return modelMap;
	}
	
	@RequestMapping("/unauth")
	public ModelMap unauth() {
		ModelMap modelMap = new ModelMap();
		/*Subject subject = SecurityUtils.getSubject();
		String sessionId = (String) subject.getSession().getId();
		System.out.println("un:"+sessionId);*/
		System.out.println("你还未登录");
		modelMap.put("state", -1);
		return modelMap;
	}
	
	@RequestMapping("/403")
	public ModelMap unauth403() {
		ModelMap modelMap = new ModelMap();
		
		System.out.println("403");
		modelMap.put("key", "403");
		return modelMap;
	}
	
}
