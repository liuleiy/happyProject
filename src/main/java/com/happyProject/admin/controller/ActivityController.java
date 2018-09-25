package com.happyProject.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happyProject.admin.model.Activity;
import com.happyProject.admin.model.Condition;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.impl.ActivityServiceImpl;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/activity")
@CrossOrigin
public class ActivityController {
	@Resource
	private ActivityServiceImpl activityService;

	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;

	@RequiresPermissions("activity.list")
	@PostMapping("/activity")
	public @ResponseBody ModelMap activity(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		ModelMap modelMap = new ModelMap();
		List<Activity> dateMap = new ArrayList<Activity>();
		PageBean<Activity> pageBean = activityService.findByDataAndCount(currentPage, pageSize, startTime, endTime);
		List<Activity> data = pageBean.getData();
		if (data != null && data.size() > 0) {
			for (Activity roundRecord : data) {
				Date ctime = roundRecord.getCtime();
				Date stime = roundRecord.getStart_time();
				Date etime = roundRecord.getEnd_time();
				Date date = TrunTime.setTime(ctime, "yyyy-MM-dd HH:mm:ss");
				Date sdate = TrunTime.setTime(stime, "yyyy-MM-dd HH:mm:ss");
				Date edate = TrunTime.setTime(etime, "yyyy-MM-dd HH:mm:ss");
				roundRecord.setCtime(date);
				roundRecord.setCtime(sdate);
				roundRecord.setCtime(edate);
				dateMap.add(roundRecord);
			}
		}
		pageBean.setData(dateMap);
		modelMap.put("data", pageBean);
		return modelMap;
	}

	@RequiresPermissions("activity.del")
	@PostMapping("/activityDel")
	public @ResponseBody ModelMap goodsDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				Activity activity = new Activity();
				activity.setId(id);
				activityService.remove(activity);

				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的活动", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

}
