package com.happyProject.admin.config;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationAccessDeniedHandler /*extends SimpleUrlAuthenticationFailureHandler{
	 @Override
	    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	                                        AuthenticationException exception) throws IOException, ServletException {

	        System.out.println("验证");

	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        response.setContentType("application/json;charset=UTF-8");
	        response.getWriter().write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员!\"}");
	    }*/
	
	 {

}
