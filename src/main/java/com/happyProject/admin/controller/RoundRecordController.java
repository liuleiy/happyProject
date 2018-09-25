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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happyProject.admin.model.Condition;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.Roles;
import com.happyProject.admin.model.RoomResultRecord;
import com.happyProject.admin.model.RoundRecord;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.service.impl.RoomResultRecordServiceImpl;
import com.happyProject.admin.service.impl.RoundRecordServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/RoundRecord")
@CrossOrigin
public class RoundRecordController {
	@Resource
	private RoundRecordServiceImpl RoundRecordService;
	// 创建房间记录
	@Resource
	private RoomResultRecordServiceImpl roomResultRecordServiceImpl;

	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;
	
	@RequiresPermissions("roundRecord.list")
	@PostMapping("/RoundRecord")
	public @ResponseBody ModelMap roundRecord(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		String roomid = condition.getRoomid();
		ModelMap modelMap = new ModelMap();
		PageBean<RoundRecord> pageBean = RoundRecordService.findByDataAndCount(currentPage, pageSize, startTime,
				endTime, roomid);
		List<RoundRecord> data = pageBean.getData();
		List<RoundRecord> dateMap = new ArrayList<RoundRecord>();
		for (RoundRecord roundRecord : data) {
			Date ctime = roundRecord.getCtime();
			Date time = TrunTime.setTime(ctime, "yyyy-MM-dd HH:mm:ss");
			roundRecord.setCtime(time);
			String rid = roundRecord.getRoomid();
			RoomResultRecord roomRecord = roomResultRecordServiceImpl.findOne(rid);
			
			if (roomRecord != null) {
				roundRecord.setRoomRecord(roomRecord);
			}else {
				roundRecord.setRoomRecord(new RoomResultRecord());
			}

			dateMap.add(roundRecord);
		}
		pageBean.setData(dateMap);
		modelMap.put("data", pageBean);
		return modelMap;
	}

	@RequiresPermissions("roundRecord.del")
	@PostMapping("/RoundRecordDel")
	public @ResponseBody ModelMap roundRecordDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				RoundRecord roundRecord = new RoundRecord();
				roundRecord.setId(id);
				RoundRecordService.remove(roundRecord);
				
				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的每局结算", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
				
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

	// details
	@RequiresPermissions("roundRecord.details")
	@GetMapping("/details")
	public @ResponseBody ModelMap details(@RequestParam(value = "id", required = false) String id) {
		RoundRecord roundRecord = RoundRecordService.findById(id, new RoundRecord());
		List<Roles> roles = roundRecord.getRoles();
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", roles);
		return modelMap;
	}

}
