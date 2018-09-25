package com.happyProject.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
import com.happyProject.admin.model.GameRoom;
import com.happyProject.admin.model.GameSend;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.impl.GameRoomServiceImpl;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.SendTheRequest;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/gameRoom")
@CrossOrigin
public class GameRoomController {
	@Resource
	private GameRoomServiceImpl gameRoomService;
	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;

	@RequiresPermissions("room.list")
	@PostMapping("/gameRoom")
	public @ResponseBody ModelMap gameRoom(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		String name = condition.getName();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		Integer del = condition.getDel();
		ModelMap modelMap = new ModelMap();
		PageBean<GameRoom> pageBean = gameRoomService.findByDataAndCount(name, currentPage, pageSize, startTime,
				endTime, del);
		modelMap.put("data", pageBean);
		return modelMap;
	}

	@RequiresPermissions("room.del")
	@PostMapping("/gameRoomDel")
	public @ResponseBody ModelMap gameRoomDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				GameRoom gameRoom = new GameRoom();
				gameRoom.setId(id);
				gameRoomService.remove(gameRoom);

				// 删除的消息告知游戏
				String id2 = gameRoom.getId();
				int gtype2 = gameRoom.getGtype();
				int rtype2 = gameRoom.getRtype();
				int dtype2 = gameRoom.getDtype();
				int ltype2 = gameRoom.getLtype();
				String name2 = gameRoom.getName();
				long status2 = gameRoom.getStatus();
				long count2 = gameRoom.getCount();
				long ante2 = gameRoom.getAnte();
				long cost2 = gameRoom.getCost();
				long vip2 = gameRoom.getVip();
				long chip2 = gameRoom.getChip();
				boolean deal2 = gameRoom.isDeal();
				long carry2 = gameRoom.getCarry();
				long down2 = gameRoom.getDown();
				long top2 = gameRoom.getTop();
				long sit2 = gameRoom.getSit();
				int del2 = gameRoom.getDel();
				String node2 = gameRoom.getNode();
				long num2 = gameRoom.getNum();
				long minimum2 = gameRoom.getMinimum();
				long maximum2 = gameRoom.getMaximum();
				Date ctime2 = gameRoom.getCtime();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
				s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
				String format = s.format(ctime2);
				GameSend gameSend = new GameSend(id2, gtype2, rtype2, dtype2, ltype2, name2, status2, count2, ante2,
						cost2, vip2, chip2, deal2, carry2, down2, top2, sit2, del2, node2, format, num2, minimum2,
						maximum2);
				SendTheRequest.delGame(7, 1, id2, gameSend);

				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的游戏房间", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

	@RequiresPermissions("room.remove")
	@PostMapping("/gameRoomEdit")
	public @ResponseBody ModelMap gameRoomEdit(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		Integer del = condition.getDel();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("del", del);
				gameRoomService.updateById(id, m, new GameRoom());

				GameRoom findById = gameRoomService.findById(id, new GameRoom());
				// 修改的消息告知游戏
				String id2 = findById.getId();
				int gtype2 = findById.getGtype();
				int rtype2 = findById.getRtype();
				int dtype2 = findById.getDtype();
				int ltype2 = findById.getLtype();
				String name2 = findById.getName();
				long status2 = findById.getStatus();
				long count2 = findById.getCount();
				long ante2 = findById.getAnte();
				long cost2 = findById.getCost();
				long vip2 = findById.getVip();
				long chip2 = findById.getChip();
				boolean deal2 = findById.isDeal();
				long carry2 = findById.getCarry();
				long down2 = findById.getDown();
				long top2 = findById.getTop();
				long sit2 = findById.getSit();
				int del2 = findById.getDel();
				String node2 = findById.getNode();
				long num2 = findById.getNum();
				long minimum2 = findById.getMinimum();
				long maximum2 = findById.getMaximum();
				Date ctime2 = findById.getCtime();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
				s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
				String format = s.format(ctime2);
				GameSend gameSend = new GameSend(id2, gtype2, rtype2, dtype2, ltype2, name2, status2, count2, ante2,
						cost2, vip2, chip2, deal2, carry2, down2, top2, sit2, del2, node2, format, num2, minimum2,
						maximum2);
				SendTheRequest.delGame(7, 0, id2, gameSend);

				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"修改了ID为" + id + "的游戏房间", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "修改成功");
		return modelMap;
	}

	// @RequiresPermissions("room.edit")
	@RequiresPermissions(value = { "room.edit", "room.add" })
	@PostMapping("/updateById")
	public @ResponseBody ModelMap updateById(@RequestBody GameRoom gameRoom, HttpServletRequest request) {
		// System.out.println("g:"+goods);
		ModelMap modelMap = new ModelMap();
		String id;
		if (gameRoom.getId() != null) {

			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"修改了ID为" + gameRoom.getId() + "的游戏房间", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);

			modelMap.put("message", "修改成功");
			id = gameRoom.getId();
			gameRoomService.AddAnaUpdate(gameRoom);
		} else {
			GameRoom addAnaUpdate = gameRoomService.AddAnaUpdate(gameRoom);
			id = addAnaUpdate.getId();
			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"添加了ID为" + addAnaUpdate.getId() + "的游戏房间", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);

			modelMap.put("message", "添加成功");
		}

		GameRoom findById = gameRoomService.findById(id, new GameRoom());
		// 添加的消息告知游戏
		String id2 = findById.getId();
		int gtype2 = findById.getGtype();
		int rtype2 = findById.getRtype();
		int dtype2 = findById.getDtype();
		int ltype2 = findById.getLtype();
		String name2 = findById.getName();
		long status2 = findById.getStatus();
		long count2 = findById.getCount();
		long ante2 = findById.getAnte();
		long cost2 = findById.getCost();
		long vip2 = findById.getVip();
		long chip2 = findById.getChip();
		boolean deal2 = findById.isDeal();
		long carry2 = findById.getCarry();
		long down2 = findById.getDown();
		long top2 = findById.getTop();
		long sit2 = findById.getSit();
		int del2 = findById.getDel();
		String node2 = findById.getNode();
		long num2 = findById.getNum();
		long minimum2 = findById.getMinimum();
		long maximum2 = findById.getMaximum();
		Date ctime2 = findById.getCtime();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
		s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String format = s.format(ctime2);
		GameSend gameSend = new GameSend(id2, gtype2, rtype2, dtype2, ltype2, name2, status2, count2, ante2, cost2,
				vip2, chip2, deal2, carry2, down2, top2, sit2, del2, node2, format, num2, minimum2, maximum2);
		SendTheRequest.delGame(7, 0, id2, gameSend);

		System.out.println("gameRoom.getId():" + gameRoom.getId());
		return modelMap;
	}

	// 根据ID查找
	@GetMapping("/findById")
	public @ResponseBody ModelMap findById(@RequestParam(value = "id", required = false) String id) {
		GameRoom gameRoom = gameRoomService.findById(id, new GameRoom());
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", gameRoom);
		return modelMap;
	}

}
