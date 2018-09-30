package com.happyProject.admin.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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

import com.happyProject.admin.model.ColUser;
import com.happyProject.admin.model.Condition;
import com.happyProject.admin.model.LogCoin;
import com.happyProject.admin.model.Order;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.RoomResultRecord;
import com.happyProject.admin.model.TopUpRanking;
import com.happyProject.admin.service.impl.ColUserServiceImpl;
import com.happyProject.admin.service.impl.LogCoinServiceImpl;
import com.happyProject.admin.service.impl.OrderServiceImpl;
import com.happyProject.admin.service.impl.RoomResultRecordServiceImpl;
import com.happyProject.admin.utlis.DateFormat;
import com.happyProject.admin.utlis.ListRepeatAdd;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/financial")
@CrossOrigin
public class FinancialController {
	// 订单
	@Resource
	private OrderServiceImpl OrderService;

	// 玩家
	@Resource
	private ColUserServiceImpl colUserService;

	@Resource
	private LogCoinServiceImpl coinServiceImpl;

	@Resource
	private RoomResultRecordServiceImpl roomResultRecordServiceImpl;

	// 综合统计
	@RequiresPermissions("comprehensive.list")
	@GetMapping("/comprehensiveAll")
	public @ResponseBody ModelMap comprehensiveAll(
			@RequestParam(value = "variable", required = false) Integer variable) {

		Date time = null;
		Integer start = null;
		Integer last = null;
		if (variable == -1) {

		} else if (variable == 30) {
			time = DateFormat.setTime(new Date(), "yyyy-MM-dd HH:mm:ss");

			start = -30;
			last = 0;
		} else if (variable == 90) {
			time = DateFormat.setTime(new Date(), "yyyy-MM-dd HH:mm:ss");
			start = -90;
			last = 0;
		}

		List<Order> findByStatusAll = OrderService.findByStatusAll(null, time, start, last);
		int moneyAll = 0;
		int validMoneyAll = 0;
		int invalidMoneyAll = 0;
		int validNumber = 0;
		int invalidNumber = 0;
		for (Order order : findByStatusAll) {
			moneyAll += order.getMoney() * 100;
			if (order.getResult() == 0) {
				validMoneyAll += order.getMoney() * 100;
				validNumber++;
			} else {
				invalidMoneyAll += order.getMoney() * 100;
				invalidNumber++;
			}
		}

		moneyAll = moneyAll / 100;
		validMoneyAll = validMoneyAll / 100;
		invalidMoneyAll = invalidMoneyAll / 100;

		List<Order> validList = OrderService.findByStatusAll(0, time, start, last);
		int userCount = validList.size();

		List<Order> orderList = ListRepeatAdd.tradeRecordAdd(validList);
		int validUserNumber = orderList.size();

		Integer userNumber = colUserService.findByStatusCount(null, time, start, last);

		Integer purchaseRate = 0;
		Integer average = 0;
		if (userNumber != 0) {
			purchaseRate = ((validUserNumber * 100) / (userNumber));
			average = ((userCount * 100) / userNumber);

		}
		Integer averageMoney = 0;
		if (average != 0) {
			averageMoney = ((int) (validMoneyAll * 100) / average);
		}
		averageMoney = averageMoney / 100;
		Map<String, Object> map = new HashMap<String, Object>();
		// 全部的售额
		map.put("moneyAll", moneyAll);
		// 有效,总数
		map.put("validNumber", validNumber);
		// 全部的售额,有效,(会员购物总额)
		map.put("validMoneyAll", validMoneyAll);
		// 无效,总数
		map.put("invalidNumber", invalidNumber);
		// 全部的售额,无效
		map.put("invalidMoneyAll", invalidMoneyAll);
		// 会员总数
		map.put("userNumber", userNumber);
		// 有订单会员数
		map.put("validUserNumber", validUserNumber);
		// 会员订单总数
		map.put("userCount", userCount);
		// 会员购买率
		map.put("purchaseRate", purchaseRate);
		// 每会员订单数
		map.put("average", average);
		// 每会员购物额
		map.put("averageMoney", averageMoney);

		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}

	// 会员排行
	@RequiresPermissions("ranking.list")
	@PostMapping("/ranking")
	public @ResponseBody ModelMap ranking(@RequestBody Condition condition) {
		String userid = condition.getUserid();
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		PageBean<Order> pageBean = OrderService.findByDataAndCount(userid, currentPage, pageSize, startTime, endTime,
				0);
		List<Order> dateMapList = pageBean.getData();
		List<TopUpRanking> dateMap = new ArrayList<TopUpRanking>();
		List<TopUpRanking> RankingList = ListRepeatAdd.getTopUpRanking(dateMapList);
		Collections.sort(RankingList);
		int firstResult = (currentPage - 1) * pageSize;
		if (RankingList != null && RankingList.size() > 0) {
			for (int i = 0; i < RankingList.size(); i++) {
				RankingList.get(i).setRanking(i + 1);
				if (firstResult <= i && (firstResult + 20) > i) {
					dateMap.add(RankingList.get(i));
				}
			}
		}

		int tm = dateMap.size();
		/*
		 * int count = tm / pageSize; if (tm % pageSize > 0) { count++; }
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", dateMap);
		map.put("allRow", tm);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", map);
		return modelMap;
	}

	@PostMapping("/allRevenue")
	public @ResponseBody ModelMap allRevenue() {
		Map<String, Object> map = new HashMap<String, Object>();
		// publicLog(map,start_time, last_time, 48);
		Date time = new Date();
		int monthNumber = DateFormat.getMonthNumber(time);
		int m = 0 - monthNumber;
		// 1. 抽水,今天
		long todayPump = 0;
		List<LogCoin> cdate = coinServiceImpl.getTimePump(0, 1, 48, time);
		for (LogCoin logCoin : cdate) {
			Integer num = Math.abs(logCoin.getNum());
			todayPump += num;
		}
		// 2. 抽水,昨天
		long yesterdayPump = 0;
		List<LogCoin> cdate2 = coinServiceImpl.getTimePump(-1, 0, 48, time);
		for (LogCoin logCoin : cdate2) {
			Integer num = Math.abs(logCoin.getNum());
			yesterdayPump += num;
		}

		// 3.总抽水
		long pump = 0;
		List<LogCoin> cdate3 = coinServiceImpl.getTimePump(null, null, 48, time);
		for (LogCoin logCoin : cdate3) {
			Integer num = Math.abs(logCoin.getNum());
			pump += num;
		}

		// 4.抽水,本月
		long monthPump = 0;
		List<LogCoin> cdate4 = coinServiceImpl.getTimePump(m, 0, 48, time);
		for (LogCoin logCoin : cdate4) {
			Integer num = Math.abs(logCoin.getNum());
			monthPump += num;
		}

		// 1. 机器人今日营收
		long robotRevenueToday = 0;
		List<RoomResultRecord> timeRevenue = roomResultRecordServiceImpl.getTimeRevenue(0, 1, time, null, null);
		for (RoomResultRecord roleRecord : timeRevenue) {
			String userid = roleRecord.getUserid();
			ColUser colUser = colUserService.findById(userid, new ColUser());
			if (colUser != null) {
				boolean robot = colUser.isRobot();
				if (robot) {
					Integer score = roleRecord.getScore();
					robotRevenueToday += score;
				}
			}
		}

		// 2.机器人昨日营收
		long robotRevenueYesterday = 0;
		List<RoomResultRecord> timeRevenue2 = roomResultRecordServiceImpl.getTimeRevenue(-1, 0, time, null, null);
		for (RoomResultRecord roleRecord : timeRevenue2) {
			String userid = roleRecord.getUserid();
			ColUser colUser = colUserService.findById(userid, new ColUser());
			if (colUser != null) {
				boolean robot = colUser.isRobot();
				if (robot) {
					Integer score = roleRecord.getScore();
					robotRevenueYesterday += score;
				}
			}
		}
		// 3.机器人总营收
		long robotRevenue = 0;
		List<RoomResultRecord> timeRevenue3 = roomResultRecordServiceImpl.getTimeRevenue(null, null, time, null, null);
		for (RoomResultRecord roleRecord : timeRevenue3) {
			String userid = roleRecord.getUserid();
			ColUser colUser = colUserService.findById(userid, new ColUser());
			if (colUser != null) {
				boolean robot = colUser.isRobot();
				if (robot) {
					Integer score = roleRecord.getScore();
					robotRevenue += score;
				}
			}
		}

		// 4.机器人本月营收
		long robotRevenueMonth = 0;
		List<RoomResultRecord> timeRevenue4 = roomResultRecordServiceImpl.getTimeRevenue(m, 0, time, null, null);
		for (RoomResultRecord roleRecord : timeRevenue4) {
			String userid = roleRecord.getUserid();
			ColUser colUser = colUserService.findById(userid, new ColUser());
			if (colUser != null) {
				boolean robot = colUser.isRobot();
				if (robot) {
					Integer score = roleRecord.getScore();
					robotRevenueMonth += score;
				}
			}
		}
		// 1.活动奖励,今天
		long todayActivity = 0;
		List<LogCoin> todayActivity55 = coinServiceImpl.getTimePump(0, 1, 57, time);
		for (LogCoin logCoin : todayActivity55) {
			Integer num = logCoin.getNum();
			todayActivity += num;
		}
		List<LogCoin> todayActivity56 = coinServiceImpl.getTimePump(0, 1, 58, time);
		for (LogCoin logCoin : todayActivity56) {
			Integer num = logCoin.getNum();
			todayActivity += num;
		}
		List<LogCoin> todayActivity57 = coinServiceImpl.getTimePump(0, 1, 59, time);
		for (LogCoin logCoin : todayActivity57) {
			Integer num = logCoin.getNum();
			todayActivity += num;
		}
		// 2.活动奖励,昨天
		long yesterdayActivity = 0;
		List<LogCoin> yesterdayActivity55 = coinServiceImpl.getTimePump(-1, 0, 57, time);
		for (LogCoin logCoin : yesterdayActivity55) {
			Integer num = logCoin.getNum();
			yesterdayActivity += num;
		}
		List<LogCoin> yesterdayActivity56 = coinServiceImpl.getTimePump(-1, 0, 58, time);
		for (LogCoin logCoin : yesterdayActivity56) {
			Integer num = logCoin.getNum();
			yesterdayActivity += num;
		}
		List<LogCoin> yesterdayActivity57 = coinServiceImpl.getTimePump(-1, 0, 59, time);
		for (LogCoin logCoin : yesterdayActivity57) {
			Integer num = logCoin.getNum();
			yesterdayActivity += num;
		}
		// 3.活动奖励,总
		long activity = 0;
		List<LogCoin> activity55 = coinServiceImpl.getTimePump(null, null, 57, time);
		for (LogCoin logCoin : activity55) {
			Integer num = logCoin.getNum();
			activity += num;
		}
		List<LogCoin> activity56 = coinServiceImpl.getTimePump(null, null, 58, time);
		for (LogCoin logCoin : activity56) {
			Integer num = logCoin.getNum();
			activity += num;
		}
		List<LogCoin> activity57 = coinServiceImpl.getTimePump(null, null, 59, time);
		for (LogCoin logCoin : activity57) {
			Integer num = logCoin.getNum();
			activity += num;
		}
		// 4.活动奖励,当月,Month
		long activityMonth = 0;
		List<LogCoin> activityMonth55 = coinServiceImpl.getTimePump(m, 1, 57, time);
		for (LogCoin logCoin : activityMonth55) {
			Integer num = logCoin.getNum();
			activityMonth += num;
		}
		List<LogCoin> activityMonth56 = coinServiceImpl.getTimePump(m, 1, 58, time);
		for (LogCoin logCoin : activityMonth56) {
			Integer num = logCoin.getNum();
			activityMonth += num;
		}
		List<LogCoin> activityMonth57 = coinServiceImpl.getTimePump(m, 1, 59, time);
		for (LogCoin logCoin : activityMonth57) {
			Integer num = logCoin.getNum();
			activityMonth += num;
		}

		// 1.三级收益,今天
		long todayThreeLevel = 0;
		List<LogCoin> todayThree = coinServiceImpl.getTimePump(0, 1, 52, time);
		for (LogCoin logCoin : todayThree) {
			Integer num = logCoin.getNum();
			todayThreeLevel += num;
		}
		// 2.三级收益,昨天
		long yesterdayThreeLevel = 0;
		List<LogCoin> yesterdayThree = coinServiceImpl.getTimePump(-1, 0, 52, time);
		for (LogCoin logCoin : yesterdayThree) {
			Integer num = logCoin.getNum();
			yesterdayThreeLevel += num;
		}
		// 3.三级收益,总
		long threeLevel = 0;
		List<LogCoin> three = coinServiceImpl.getTimePump(null, null, 52, time);
		for (LogCoin logCoin : three) {
			Integer num = logCoin.getNum();
			threeLevel += num;
		}
		// 4.三级收益,当月
		long monthThreeLevel = 0;
		List<LogCoin> monthThree = coinServiceImpl.getTimePump(m, 1, 52, time);
		for (LogCoin logCoin : monthThree) {
			Integer num = logCoin.getNum();
			monthThreeLevel += num;
		}

		// 1.区域奖励,今天
		long todayRegion = 0;
		List<LogCoin> todayRegions = coinServiceImpl.getTimePump(0, 1, 53, time);
		for (LogCoin logCoin : todayRegions) {
			Integer num = logCoin.getNum();
			todayRegion += num;
		}
		// 2.区域奖励,昨天
		long yesterdayRegion = 0;
		List<LogCoin> yesterdayRegions = coinServiceImpl.getTimePump(-1, 0, 53, time);
		for (LogCoin logCoin : yesterdayRegions) {
			Integer num = logCoin.getNum();
			yesterdayRegion += num;
		}
		// 3.区域奖励,总
		long region = 0;
		List<LogCoin> regions = coinServiceImpl.getTimePump(null, null, 53, time);
		for (LogCoin logCoin : regions) {
			Integer num = logCoin.getNum();
			region += num;
		}
		// 4.区域奖励,当月
		long monthRegion = 0;
		List<LogCoin> monthRegions = coinServiceImpl.getTimePump(m, 1, 53, time);
		for (LogCoin logCoin : monthRegions) {
			Integer num = logCoin.getNum();
			monthRegion += num;
		}

		// 1.幸运星,今天
		long todayLucky = 0;
		List<LogCoin> todayLuckys = coinServiceImpl.getTimePump(0, 1, 51, time);
		for (LogCoin logCoin : todayLuckys) {
			Integer num = logCoin.getNum();
			todayLucky += num;
		}
		// 2.幸运星,昨天
		long yesterdayLucky = 0;
		List<LogCoin> yesterdayLuckys = coinServiceImpl.getTimePump(-1, 0, 51, time);
		for (LogCoin logCoin : yesterdayLuckys) {
			Integer num = logCoin.getNum();
			yesterdayLucky += num;
		}
		// 3.幸运星,总
		long lucky = 0;
		List<LogCoin> luckys = coinServiceImpl.getTimePump(null, null, 51, time);
		for (LogCoin logCoin : luckys) {
			Integer num = logCoin.getNum();
			lucky += num;
		}
		// 4.幸运星,当月
		long monthLucky = 0;
		List<LogCoin> monthLuckys = coinServiceImpl.getTimePump(m, 1, 51, time);
		for (LogCoin logCoin : monthLuckys) {
			Integer num = logCoin.getNum();
			monthLucky += num;
		}

		// 1.任务奖励,今天
		long todayTask = 0;
		List<LogCoin> todayTasks = coinServiceImpl.getTimePump(0, 1, 46, time);
		for (LogCoin logCoin : todayTasks) {
			Integer num = logCoin.getNum();
			todayTask += num;
		}
		// 2.任务奖励,昨天
		long yesterdayTask = 0;
		List<LogCoin> yesterdayTasks = coinServiceImpl.getTimePump(-1, 0, 46, time);
		for (LogCoin logCoin : yesterdayTasks) {
			Integer num = logCoin.getNum();
			yesterdayTask += num;
		}
		// 3.任务奖励,总
		long task = 0;
		List<LogCoin> tasks = coinServiceImpl.getTimePump(null, null, 46, time);
		for (LogCoin logCoin : tasks) {
			Integer num = logCoin.getNum();
			task += num;
		}
		// 4.任务奖励,当月
		long monthTask = 0;
		List<LogCoin> monthTasks = coinServiceImpl.getTimePump(m, 1, 46, time);
		for (LogCoin logCoin : monthTasks) {
			Integer num = logCoin.getNum();
			monthTask += num;
		}

		// 1.连续登录奖励,今天
		long todayLogin = 0;
		List<LogCoin> todayLogins = coinServiceImpl.getTimePump(0, 1, 47, time);
		for (LogCoin logCoin : todayLogins) {
			Integer num = logCoin.getNum();
			todayLogin += num;
		}
		// 2.连续登录奖励,昨天
		long yesterdayLogin = 0;
		List<LogCoin> yesterdayLogins = coinServiceImpl.getTimePump(-1, 0, 47, time);
		for (LogCoin logCoin : yesterdayLogins) {
			Integer num = logCoin.getNum();
			yesterdayLogin += num;
		}
		// 3.连续登录奖励,总
		long login = 0;
		List<LogCoin> logins = coinServiceImpl.getTimePump(null, null, 47, time);
		for (LogCoin logCoin : logins) {
			Integer num = logCoin.getNum();
			login += num;
		}
		// 4.连续登录奖励,当月
		long monthLogin = 0;
		List<LogCoin> monthLogins = coinServiceImpl.getTimePump(m, 1, 47, time);
		for (LogCoin logCoin : monthLogins) {
			Integer num = logCoin.getNum();
			monthLogin += num;
		}
		// 1.绑定奖金,邀请,今天
		long todayInvitation = 0;
		List<LogCoin> todayInvitations = coinServiceImpl.getTimePump(0, 1, 55, time);
		for (LogCoin logCoin : todayInvitations) {
			Integer num = logCoin.getNum();
			todayInvitation += num;
		}
		// 2.绑定奖金,邀请,昨天
		long yesterdayInvitation = 0;
		List<LogCoin> yesterdayInvitations = coinServiceImpl.getTimePump(-1, 0, 55, time);
		for (LogCoin logCoin : yesterdayInvitations) {
			Integer num = logCoin.getNum();
			yesterdayInvitation += num;
		}
		// 3.绑定奖金,邀请,总
		long invitation = 0;
		List<LogCoin> invitations = coinServiceImpl.getTimePump(null, null, 55, time);
		for (LogCoin logCoin : invitations) {
			Integer num = logCoin.getNum();
			invitation += num;
		}
		// 4.绑定奖金,邀请,当月
		long monthInvitation = 0;
		List<LogCoin> monthInvitations = coinServiceImpl.getTimePump(m, 1, 55, time);
		for (LogCoin logCoin : monthInvitations) {
			Integer num = logCoin.getNum();
			monthInvitation += num;
		}
		// as
		// 总营收 = 抽水 + 机器人赢收 - 活动奖励 - 三级收益 - 区域奖励 - 幸运星 - 任务奖励 - 连续登陆奖励 - 邀请
		long all = pump + robotRevenue - activity - threeLevel - region - lucky - task - login - invitation;
		// 今日总营收
		long t = todayPump + robotRevenueToday - todayActivity - todayThreeLevel - todayRegion - todayLucky - todayTask
				- todayLogin - todayInvitation;
		// 昨日总营收
		long y = yesterdayPump + robotRevenueYesterday - yesterdayActivity - yesterdayThreeLevel - yesterdayRegion
				- yesterdayLucky - yesterdayTask - yesterdayLogin - yesterdayInvitation;
		// 本月总营收
		long mp = monthPump + robotRevenueMonth - activityMonth - monthThreeLevel - monthRegion - monthLucky - monthTask
				- monthLogin - monthInvitation;
		// 营收
		map.put("all", all);
		map.put("t", t);
		map.put("y", y);
		map.put("mp", mp);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", map);
		return modelMap;
	}

	// reportform
	@RequiresPermissions("revenueStatement.list")
	@PostMapping("/reportform")
	public @ResponseBody ModelMap reportform() {
		Map<String, Object> map = new HashMap<String, Object>();

		Date time = new Date();
		// 总营收 = 抽水 + 机器人赢收 - 活动奖励 - 三级收益 - 区域奖励 - 幸运星 - 任务奖励 - 连续登陆奖励-邀请
		// 3.总抽水
		long pump = 0;
		List<LogCoin> cdate3 = coinServiceImpl.getTimePump(null, null, 48, time);
		for (LogCoin logCoin : cdate3) {
			Integer num = Math.abs(logCoin.getNum());
			pump += num;
		}

		// 3.机器人
		long robotRevenue = 0;
		List<RoomResultRecord> timeRevenue3 = roomResultRecordServiceImpl.getTimeRevenue(null, null, time, null, null);
		for (RoomResultRecord roleRecord : timeRevenue3) {
			String userid = roleRecord.getUserid();
			ColUser colUser = colUserService.findById(userid, new ColUser());
			if (colUser != null) {
				boolean robot = colUser.isRobot();
				if (robot) {
					Integer score = roleRecord.getScore();
					robotRevenue += score;
				}
			}
		}

		// 3.活动奖励,总
		long activity = 0;
		List<LogCoin> activity55 = coinServiceImpl.getTimePump(null, null, 57, time);
		for (LogCoin logCoin : activity55) {
			Integer num = logCoin.getNum();
			activity += num;
		}
		List<LogCoin> activity56 = coinServiceImpl.getTimePump(null, null, 58, time);
		for (LogCoin logCoin : activity56) {
			Integer num = logCoin.getNum();
			activity += num;
		}
		List<LogCoin> activity57 = coinServiceImpl.getTimePump(null, null, 59, time);
		for (LogCoin logCoin : activity57) {
			Integer num = logCoin.getNum();
			activity += num;
		}

		// 3.三级收益,总
		long threeLevel = 0;
		List<LogCoin> three = coinServiceImpl.getTimePump(null, null, 52, time);
		for (LogCoin logCoin : three) {
			Integer num = logCoin.getNum();
			threeLevel += num;
		}

		// 3.区域奖励,总
		long region = 0;
		List<LogCoin> regions = coinServiceImpl.getTimePump(null, null, 53, time);
		for (LogCoin logCoin : regions) {
			Integer num = logCoin.getNum();
			region += num;
		}

		// 3.幸运星,总
		long lucky = 0;
		List<LogCoin> luckys = coinServiceImpl.getTimePump(null, null, 51, time);
		for (LogCoin logCoin : luckys) {
			Integer num = logCoin.getNum();
			lucky += num;
		}

		// 3.任务奖励,总
		long task = 0;
		List<LogCoin> tasks = coinServiceImpl.getTimePump(null, null, 46, time);
		for (LogCoin logCoin : tasks) {
			Integer num = logCoin.getNum();
			task += num;
		}
		// 3.连续登录奖励,总
		long login = 0;
		List<LogCoin> logins = coinServiceImpl.getTimePump(null, null, 47, time);
		for (LogCoin logCoin : logins) {
			Integer num = logCoin.getNum();
			login += num;
		}

		// 3.绑定奖金,邀请,总
		long invitation = 0;
		List<LogCoin> invitations = coinServiceImpl.getTimePump(null, null, 55, time);
		for (LogCoin logCoin : invitations) {
			Integer num = logCoin.getNum();
			invitation += num;
		}
		long all = pump + robotRevenue - activity - threeLevel - region - lucky - task - login - invitation;
		map.put("pump", pump);
		map.put("robotRevenue", robotRevenue);
		map.put("activity", activity);
		map.put("threeLevel", threeLevel);
		map.put("region", region);
		map.put("lucky", lucky);
		map.put("task", task);
		map.put("login", login);
		map.put("invitation", invitation);
		map.put("all", all);

		ModelMap modelMap = new ModelMap();
		modelMap.put("data", map);
		return modelMap;
	}

	private ModelMap publicLogpublicLog(Integer currentPage, Integer pageSize, Long startTime, Long endTime,
			Integer type) {

		PageBean<LogCoin> pageBean = coinServiceImpl.findByDataAndCount(null, currentPage, pageSize, startTime, endTime,
				type);
		List<LogCoin> data = pageBean.getData();
		List<LogCoin> dateMap = new ArrayList<LogCoin>();
		if (data != null && data.size() > 0) {
			for (LogCoin roundRecord : data) {
				Date ctime = roundRecord.getCtime();
				Integer num = Math.abs(roundRecord.getNum());
				Date date = TrunTime.setTime(ctime, "yyyy-MM-dd HH:mm:ss");
				roundRecord.setCtime(date);
				roundRecord.setNum(num);
				dateMap.add(roundRecord);
			}
		}

		pageBean.setData(dateMap);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}

	// pumpRevenue
	@RequiresPermissions("revenueStatement.pumpRevenue")
	@PostMapping("/pumpRevenue")
	public @ResponseBody ModelMap pumpRevenue(@RequestBody Condition condition) {

		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		ModelMap modelMap = publicLogpublicLog(currentPage, pageSize, startTime, endTime, 48);
		return modelMap;
	}

	// robotRevenue,,,,,,,,,,,,,,,,
	@RequiresPermissions("revenueStatement.robotRevenue")
	@PostMapping("/robotRevenue")
	public @ResponseBody ModelMap robotRevenue(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		// ModelMap modelMap = publicLogpublicLog(currentPage, pageSize, startTime,
		// endTime, 48);
		List<RoomResultRecord> timeRevenue = roomResultRecordServiceImpl.getTimeRevenue(null, null, null, startTime,
				endTime);
		List<RoomResultRecord> dateMap = new ArrayList<RoomResultRecord>();
		List<RoomResultRecord> rr = new ArrayList<RoomResultRecord>();
		for (RoomResultRecord roleRecord : timeRevenue) {
			String userid = roleRecord.getUserid();
			ColUser colUser = colUserService.findById(userid, new ColUser());
			if (colUser != null) {
				boolean robot = colUser.isRobot();
				if (robot) {
					// Integer score = roleRecord.getScore();
					rr.add(roleRecord);
				}
			}
		}
		// 百人场的机器人
		List<LogCoin> timePump = coinServiceImpl.getTimePump(null, null, 6, null);
		for (LogCoin logCoin : timePump) {
			String userid = logCoin.getUserid();
			ColUser colUser = colUserService.findById(userid, new ColUser());
			if (colUser != null) {
				boolean robot = colUser.isRobot();
				if (robot) {
					// Integer score = roleRecord.getScore();
					String userid2 = logCoin.getUserid();
					Integer num = logCoin.getNum();
					Date ctime = logCoin.getCtime();
					RoomResultRecord r = new RoomResultRecord();
					r.setUserid(userid2);
					r.setScore(num);
					r.setCtime(ctime);
					rr.add(r);
				}
			}
		}

		if (rr != null && rr.size() > 0) {
			for (int i = 0; i < rr.size(); i++) {
				if (currentPage <= i && (currentPage + pageSize) > i) {
					RoomResultRecord roleRecord = timeRevenue.get(i);
					Date ctime = roleRecord.getCtime();
					Date date = TrunTime.setTime(ctime, "yyyy-MM-dd HH:mm:ss");
					roleRecord.setCtime(date);
					dateMap.add(roleRecord);
				}
			}
		}
		int tm = rr.size();

		PageBean<RoomResultRecord> pageBean = new PageBean<RoomResultRecord>(dateMap, tm, 0, currentPage, pageSize);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}

	// activityRevenue,活动
	@RequiresPermissions("revenueStatement.activityRevenue")
	@PostMapping("/activityRevenue")
	public @ResponseBody ModelMap activityRevenue(@RequestBody Condition condition) {

		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();

		PageBean<LogCoin> pageBean = coinServiceImpl.findByDataAndCount(null, currentPage, pageSize, startTime, endTime,
				57);
		PageBean<LogCoin> pageBean2 = coinServiceImpl.findByDataAndCount(null, currentPage, pageSize, startTime,
				endTime, 58);
		PageBean<LogCoin> pageBean3 = coinServiceImpl.findByDataAndCount(null, currentPage, pageSize, startTime,
				endTime, 59);
		List<LogCoin> data = pageBean.getData();
		List<LogCoin> data2 = pageBean2.getData();
		List<LogCoin> data3 = pageBean3.getData();
		List<LogCoin> dateMap = new ArrayList<LogCoin>();
		for (LogCoin logCoin : data) {
			Date ctime = logCoin.getCtime();
			Integer num = Math.abs(logCoin.getNum());
			Date date = TrunTime.setTime(ctime, "yyyy-MM-dd HH:mm:ss");
			logCoin.setCtime(date);
			logCoin.setNum(num);
			dateMap.add(logCoin);
		}
		for (LogCoin logCoin : data2) {
			Date ctime = logCoin.getCtime();
			Integer num = Math.abs(logCoin.getNum());
			Date date = TrunTime.setTime(ctime, "yyyy-MM-dd HH:mm:ss");
			logCoin.setCtime(date);
			logCoin.setNum(num);
			dateMap.add(logCoin);
		}
		for (LogCoin logCoin : data3) {
			Date ctime = logCoin.getCtime();
			Integer num = Math.abs(logCoin.getNum());
			Date date = TrunTime.setTime(ctime, "yyyy-MM-dd HH:mm:ss");
			logCoin.setCtime(date);
			logCoin.setNum(num);
			dateMap.add(logCoin);
		}
		int allRow = data.size() + data2.size() + data3.size();
		pageBean.setData(dateMap);
		pageBean.setAllRow(allRow);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}

	// threeLevelRevenue,三级收益
	@RequiresPermissions("revenueStatement.threeLevelRevenue")
	@PostMapping("/threeLevelRevenue")
	public @ResponseBody ModelMap threeLevelRevenue(@RequestBody Condition condition) {

		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		ModelMap modelMap = publicLogpublicLog(currentPage, pageSize, startTime, endTime, 52);
		return modelMap;
	}

	// regionRevenue,区域奖励
	@RequiresPermissions("revenueStatement.regionRevenue")
	@PostMapping("/regionRevenue")
	public @ResponseBody ModelMap regionRevenue(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		ModelMap modelMap = publicLogpublicLog(currentPage, pageSize, startTime, endTime, 53);
		return modelMap;
	}

	// luckyRevenue,牌型奖励
	@RequiresPermissions("revenueStatement.luckyRevenue")
	@PostMapping("/luckyRevenue")
	public @ResponseBody ModelMap luckyRevenue(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		ModelMap modelMap = publicLogpublicLog(currentPage, pageSize, startTime, endTime, 51);
		return modelMap;
	}

	// taskRevenue,任务奖励
	@RequiresPermissions("revenueStatement.taskRevenue")
	@PostMapping("/taskRevenue")
	public @ResponseBody ModelMap taskRevenue(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		ModelMap modelMap = publicLogpublicLog(currentPage, pageSize, startTime, endTime, 46);
		return modelMap;
	}

	// loginRevenue,登陆签到奖励
	@RequiresPermissions("revenueStatement.loginRevenue")
	@PostMapping("/loginRevenue")
	public @ResponseBody ModelMap loginRevenue(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		ModelMap modelMap = publicLogpublicLog(currentPage, pageSize, startTime, endTime, 47);
		return modelMap;
	}

	// invitationRevenue,邀请奖励
	@RequiresPermissions("revenueStatement.invitationRevenue")
	@PostMapping("/invitationRevenue")
	public @ResponseBody ModelMap invitationRevenue(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		ModelMap modelMap = publicLogpublicLog(currentPage, pageSize, startTime, endTime, 55);
		return modelMap;
	}
}
