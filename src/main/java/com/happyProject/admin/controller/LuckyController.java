package com.happyProject.admin.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happyProject.admin.model.Condition;
import com.happyProject.admin.model.Lucky;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.impl.LuckyServiceImpl;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/lucky")
@CrossOrigin
public class LuckyController {
	@Resource
	private LuckyServiceImpl luckyService;

	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;

	@RequiresPermissions("lucky.list")
	@PostMapping("/lucky")
	public @ResponseBody ModelMap lucky(@RequestBody Condition condition) {
		System.out.println("order");
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		PageBean<Lucky> pageBean = luckyService.findByDataAndCount(currentPage, pageSize, startTime, endTime);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}

	@RequiresPermissions("lucky.del")
	@PostMapping("/luckyDel")
	public @ResponseBody ModelMap luckyDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				Lucky lucky = new Lucky();
				lucky.setId(id);
				luckyService.remove(lucky);

				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的幸运星奖励", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);

			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

	@GetMapping("/findById")
	public @ResponseBody ModelMap findById(@RequestParam(value = "id", required = false) String id) {
		Lucky lucky = luckyService.findById(id, new Lucky());
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", lucky);
		return modelMap;
	}

	// 判断是否存在
	@GetMapping("/isLuckid")
	public @ResponseBody ModelMap isLuckid(@RequestParam(value = "id", required = false) Integer id) {
		ModelMap modelMap = new ModelMap();
		Lucky luckid = luckyService.isLuckid("luckyid", id);
		if (luckid != null) {
			modelMap.put("message", true);
		} else {
			modelMap.put("message", false);
		}
		return modelMap;
	}

	@RequiresPermissions("lucky.add")
	@PostMapping("/updateById")
	public @ResponseBody ModelMap updateById(@RequestBody Lucky lucky, HttpServletRequest request) {
		// System.out.println("g:"+goods);
		ModelMap modelMap = new ModelMap();
		if (lucky.getId() != null) {
			modelMap.put("message", "修改成功");
			luckyService.AddAnaUpdate(lucky);
			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"修改了ID为" + lucky.getId() + "的幸运星奖励", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);
		} else {
			modelMap.put("message", "添加成功");
			Lucky addAnaUpdate = luckyService.AddAnaUpdate(lucky);
			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"添加了ID为" + addAnaUpdate.getId() + "的幸运星奖励", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);
		}
		return modelMap;
	}

}
