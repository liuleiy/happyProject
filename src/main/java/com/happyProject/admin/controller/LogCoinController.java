package com.happyProject.admin.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happyProject.admin.model.Condition;
import com.happyProject.admin.model.LogCoin;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.impl.LogCoinServiceImpl;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;

@RestController
@RequestMapping("/logCoin")
@CrossOrigin
public class LogCoinController {
	
	@Resource
	private LogCoinServiceImpl logCoinService;

	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;
	
	@RequiresPermissions("hundred.list")
	@PostMapping("/logCoin")
	public @ResponseBody ModelMap logCoin(@RequestBody Condition condition) {
		System.out.println("logCoin");
		String userid = condition.getUserid();
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		PageBean<LogCoin> pageBean = logCoinService.findByDataAndCount(userid, currentPage, pageSize, startTime,
				endTime,null);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}
	
	/*@PostMapping("/logCoinDel")
	public @ResponseBody ModelMap logCoinDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				LogCoin logCoin = new LogCoin();
				logCoin.setId(id);
				logCoinService.remove(logCoin);
				
				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的百人场记录", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
				
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}*/
	
}
