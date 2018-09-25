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
import com.happyProject.admin.model.CreateRoomRecord;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.impl.CreateRoomRecordServiceImpl;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/createRoomRecord")
@CrossOrigin
public class CreateRoomRecordController {
	@Resource
	private CreateRoomRecordServiceImpl createRoomRecordServiceImpl;
	
	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;
	
	@RequiresPermissions("createRoom.list")
	@PostMapping("/createRoomRecord")
	public @ResponseBody ModelMap createRoomRecord(@RequestBody Condition condition) {
		System.out.println("createRoomRecord");
		String code = condition.getCode();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		ModelMap modelMap = new ModelMap();
		PageBean<CreateRoomRecord> pageBean = createRoomRecordServiceImpl.findByDataAndCount(code, currentPage, pageSize);
		modelMap.put("data", pageBean);
		return modelMap;
	}
	
	@RequiresPermissions("createRoom.del")
	@PostMapping("/createRoomRecordDel")
	public @ResponseBody ModelMap goodsDel(@RequestBody Condition condition, HttpServletRequest request) {
		System.out.println("createRoomRecordDel");
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				CreateRoomRecord createRoomRecord = new CreateRoomRecord();
				createRoomRecord.setId(id);
				createRoomRecordServiceImpl.remove(createRoomRecord);
				
				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的创建房间记录", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}
}
