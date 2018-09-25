package com.happyProject.admin.anomaly;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@ResponseBody
public class GlobalAnomaly {
	/*@ExceptionHandler(value = Exception.class)
	public String allExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
		//exception.printStackTrace();
		System.out.println("我报错了：" + exception.getLocalizedMessage());
		System.out.println("我报错了：" + exception.getCause());
		System.out.println("我报错了：" + exception.getSuppressed());
		System.out.println("我报错了：" + exception.getMessage());
		System.out.println("我报错了：" + exception.getStackTrace());
		return "服务器异常，请联系管理员！";
	}*/
	//AuthorizationException,未授权异常
	@ExceptionHandler(value = AuthorizationException.class)
	public ModelMap AuthorizationExceptionExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
		ModelMap ModelMap = new ModelMap();
		System.out.println("未授权");
		ModelMap.put("state", 2);
		return ModelMap;
	}
	/*@ExceptionHandler(value = DisabledAccountException.class)
	public ModelMap DisabledAccountExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
		ModelMap ModelMap = new ModelMap();
		System.out.println("以及禁止");
		ModelMap.put("state", 2);
		return ModelMap;
	}*/
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelMap NoHandlerFoundExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
		ModelMap ModelMap = new ModelMap();
		System.out.println("未找到资源");
		ModelMap.put("state", 404);
		return ModelMap;
	}
	
	/*@ExceptionHandler(Exception.class)
	public ModelMap ExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
		ModelMap ModelMap = new ModelMap();
		System.out.println("服务器异常");
		ModelMap.put("state", 500);
		return ModelMap;
	}*/
}
