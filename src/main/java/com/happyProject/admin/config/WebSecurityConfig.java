package com.happyProject.admin.config;


public class WebSecurityConfig /*extends WebSecurityConfigurerAdapter {
	 @Autowired
	 AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
	
	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
	     System.out.println("进来");
		http.formLogin()                   //  定义当需要用户登录时候，转到的登录页面。
	             .loginPage("/login.html")           // 设置登录页面
	             .loginProcessingUrl("/user/login")  // 自定义的登录接口
	     		 .failureHandler(new AuthenticationFailureHandler() {

					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException exception) throws IOException, ServletException {
						System.out.println("成功");
						response.setContentType("application/json;charset=utf-8");
		                PrintWriter out = response.getWriter();
		                StringBuffer sb = new StringBuffer();
		                sb.append("{\"status\":\"error\",\"msg\":\"");
		                if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
		                    sb.append("用户名或密码输入错误，登录失败!");
		                } else if (exception instanceof DisabledException) {
		                    sb.append("账户被禁用，登录失败，请联系管理员!");
		                } else {
		                    sb.append("登录失败!");
		                }
		                sb.append("\"}");
		                out.write(sb.toString());
		                out.flush();
		                out.close();
						
					}})//成功
	     		 .successHandler(new AuthenticationSuccessHandler() {

					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						System.out.println("失败");
						response.setContentType("application/json;charset=utf-8");
			                PrintWriter out = response.getWriter();
			               // ObjectMapper objectMapper = new ObjectMapper();
			                String s = "{\"status\":\"success\",\"msg\":成功}";
			                out.write(s);
			                out.flush();
			                out.close();
					}})//失败
	             .and()
	             .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
	             .antMatchers("/login.html").permitAll()     // 设置所有人都可以访问登录页面
	             .anyRequest()               // 任何请求,登录后可以访问
	             .authenticated()
	             .and()
	             .csrf().disable();          // 关闭csrf防护
	 }*/
{
}
