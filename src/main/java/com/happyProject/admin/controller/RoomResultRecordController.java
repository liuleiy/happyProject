package com.happyProject.admin.controller;

import java.util.Date;

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

import com.happyProject.admin.model.Condition;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.RoomResultRecord;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.service.impl.RoomResultRecordServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/roomResultRecord")
@CrossOrigin
public class RoomResultRecordController {
	@Resource
	private RoomResultRecordServiceImpl roomResultRecordService;

	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;
	
	@RequiresPermissions("roomResult.list")
	@PostMapping("/roomResultRecord")
	public @ResponseBody ModelMap roomResultRecord(@RequestBody Condition condition) {
		System.out.println("roomResultRecord");
		String userid = condition.getUserid();
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		PageBean<RoomResultRecord> pageBean = roomResultRecordService.findByDataAndCount(userid, currentPage, pageSize, startTime,
				endTime);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}
	
	@RequiresPermissions("roomResult.del")
	@PostMapping("/roomResultRecordDel")
	public @ResponseBody ModelMap goodsDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				RoomResultRecord roomResultRecord = new RoomResultRecord();
				roomResultRecord.setId(id);
				roomResultRecordService.remove(roomResultRecord);
				
				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的房间结果记录", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
				
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}
	
}
