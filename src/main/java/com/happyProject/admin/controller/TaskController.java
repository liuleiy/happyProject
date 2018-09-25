package com.happyProject.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.happyProject.admin.model.LoginPrize;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.model.Task;
import com.happyProject.admin.model.TaskSend;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.service.impl.SequenceServiceImpl;
import com.happyProject.admin.service.impl.TaskServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.SendTheRequest;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {
	@Resource
	private TaskServiceImpl taskServiceImpl;

	@Resource
	private SequenceServiceImpl sequenceServiceImpl;

	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;

	@RequiresPermissions("task.list")
	@PostMapping("/task")
	public @ResponseBody ModelMap task(@RequestBody Condition condition) {
		System.out.println("order");
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		PageBean<Task> pageBean = taskServiceImpl.findByDataAndCount(currentPage, pageSize, startTime, endTime);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}

	@RequiresPermissions("task.del")
	@PostMapping("/taskDel")
	public @ResponseBody ModelMap taskDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				Task task = new Task();
				task.setId(id);
				taskServiceImpl.remove(task);

				// 删除的消息告知游戏
				String id2 = task.getId();
				Integer taskid2 = task.getTaskid();
				Integer type2 = task.getType();
				String name2 = task.getName();
				Integer count2 = task.getCount();
				Long diamond2 = task.getDiamond();
				Long coin2 = task.getCoin();
				Boolean today2 = task.getToday();
				Integer del2 = task.getDel();
				Integer nextid2 = task.getNextid();
				Date ctime2 = task.getCtime();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
				s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
				String format = s.format(ctime2);
				TaskSend taskSend = new TaskSend(id2, taskid2, type2, name2, count2, diamond2, coin2, today2, del2,
						nextid2, format);
				SendTheRequest.delTask(10, 1, String.valueOf(taskid2), taskSend);

				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的任务", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);

			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

	@GetMapping("/findById")
	public @ResponseBody ModelMap findById(@RequestParam(value = "id", required = false) String id) {
		Task task = taskServiceImpl.findById(id, new Task());
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", task);
		return modelMap;
	}

	@RequiresPermissions(value = { "task.edit", "task.add" })
//	@RequiresPermissions("task.edit")
	@PostMapping("/updateById")
	public @ResponseBody ModelMap updateById(@RequestBody Task task, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		String nextIdStr = sequenceServiceImpl.nextId("taskid");
		int nextId = Integer.parseInt(nextIdStr);
		if (task.getId() != null) {
			modelMap.put("message", "修改成功");
			taskServiceImpl.AddAnaUpdate(task);

			Task findById = taskServiceImpl.findById(task.getId(), new Task());
			// 修改的消息告知游戏
			String id2 = findById.getId();
			Integer taskid2 = findById.getTaskid();
			Integer type2 = findById.getType();
			String name2 = findById.getName();
			Integer count2 = findById.getCount();
			Long diamond2 = findById.getDiamond();
			Long coin2 = findById.getCoin();
			Boolean today2 = findById.getToday();
			Integer del2 = findById.getDel();
			Integer nextid2 = findById.getNextid();
			Date ctime2 = findById.getCtime();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
			s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			String format = s.format(ctime2);
			TaskSend taskSend = new TaskSend(id2, taskid2, type2, name2, count2, diamond2, coin2, today2, del2, nextid2,
					format);
			SendTheRequest.delTask(10, 0, String.valueOf(taskid2), taskSend);

			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"修改了ID为" + task.getId() + "的任务", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);

		} else {
			Integer type = task.getType();// 任务类型
			List<Task> taskByType = taskServiceImpl.findByType(type);
			Integer count = task.getCount();// 任务完成的数值
			if (taskByType == null || taskByType.size() <= 0) {// 代表无此类型的任务
				task.setNextid(0);
			} else {
				List<Task> upTask = new ArrayList<Task>();
				List<Task> nextTask = new ArrayList<Task>();
				List<Task> equalTask = new ArrayList<Task>();
				for (Task t : taskByType) {
					Integer c = t.getCount();
					if (c > count) {// 比输入的数值大
						nextTask.add(t);
					} else if (c < count) {// 比输入的数值小
						upTask.add(t);
					} else {
						equalTask.add(t);
					}
				}

				if (upTask.size() > 0) {
					Task up = upTask.get(upTask.size() - 1);
					up.setNextid(nextId);
					taskServiceImpl.AddAnaUpdate(up);
				} else {

				}

				if (nextTask.size() > 0) {
					Task next = nextTask.get(0);
					Integer taskid = next.getTaskid();
					task.setNextid(taskid);
				} else {
					task.setNextid(0);
				}

				if (equalTask.size() > 0) {
					Task next = upTask.get(0);
					Integer taskid = next.getTaskid();
					task.setNextid(taskid);
				} else {

				}

			}

			task.setCtime(new Date());
			task.setTaskid(nextId);
			modelMap.put("message", "添加成功");
			Task addAnaUpdate = taskServiceImpl.AddAnaUpdate(task);
			sequenceServiceImpl.nextOne("taskid", nextIdStr);

			// 添加的消息告知游戏
			String id2 = addAnaUpdate.getId();
			Integer taskid2 = addAnaUpdate.getTaskid();
			Integer type2 = addAnaUpdate.getType();
			String name2 = addAnaUpdate.getName();
			Integer count2 = addAnaUpdate.getCount();
			Long diamond2 = addAnaUpdate.getDiamond();
			Long coin2 = addAnaUpdate.getCoin();
			Boolean today2 = addAnaUpdate.getToday();
			Integer del2 = addAnaUpdate.getDel();
			Integer nextid2 = addAnaUpdate.getNextid();
			Date ctime2 = addAnaUpdate.getCtime();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
			s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			String format = s.format(ctime2);
			TaskSend taskSend = new TaskSend(id2, taskid2, type2, name2, count2, diamond2, coin2, today2, del2, nextid2,
					format);
			SendTheRequest.delTask(10, 0, String.valueOf(taskid2), taskSend);

			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"添加了ID为" + addAnaUpdate.getId() + "的任务", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);

		}

		return modelMap;
	}

	// setLoginPrize
	// 设置连续登陆
	@RequiresPermissions("loginPrize.list")
	@PostMapping("/setLoginPrize")
	public @ResponseBody ModelMap setLoginPrize(@RequestBody Condition condition) {
		Integer coin = condition.getCoin();
		Integer diamond = condition.getDiamond();
		Integer day = condition.getDay();
		Date ctime = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		// 注意格式化的表达式
		String format2 = s.format(ctime);
		LoginPrize loginPrize = new LoginPrize("", day, 0, coin, diamond, format2);
		SendTheRequest.setLoginPrize(String.valueOf(day), loginPrize);

		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "设置成功");
		return modelMap;
	}

}
