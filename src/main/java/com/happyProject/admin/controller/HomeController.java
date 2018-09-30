package com.happyProject.admin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.happyProject.admin.model.ColUser;
import com.happyProject.admin.model.LogCoin;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.OrderWeekNumber;
import com.happyProject.admin.model.Roles;
import com.happyProject.admin.model.RoomResultRecord;
import com.happyProject.admin.model.RoundRecord;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.model.WinAndLose;
import com.happyProject.admin.service.impl.ColUserServiceImpl;
import com.happyProject.admin.service.impl.LogCoinServiceImpl;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.service.impl.OrderServiceImpl;
import com.happyProject.admin.service.impl.RoomResultRecordServiceImpl;
import com.happyProject.admin.service.impl.RoundRecordServiceImpl;
import com.happyProject.admin.service.impl.TUserServiceImpl;
import com.happyProject.admin.utlis.DateFormat;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.ListRepeatAdd;
import com.happyProject.admin.utlis.Md5Util;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/home")
@CrossOrigin
public class HomeController {
	// 后台管理
	@Resource
	private TUserServiceImpl tUserServiceImpl;
	// 玩家,代理
	@Resource
	private ColUserServiceImpl userServiceImpl;
	// 订单
	@Resource
	private OrderServiceImpl orderServiceImpl;

	@Resource
	private RoundRecordServiceImpl roundRecordServiceImpl;

	@Resource
	private LogCoinServiceImpl coinServiceImpl;

	@Resource
	private RoomResultRecordServiceImpl roomResultRecordServiceImpl;
	
	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;
	
	// findById
	@GetMapping("/findById")
	public @ResponseBody ModelMap findById(HttpServletRequest request) {
		HttpSession session = request.getSession();
		TUser tUser = (TUser) session.getAttribute("tUser");
		String id = tUser.getId();

		TUser tuser = tUserServiceImpl.findById(id, new TUser());
		tuser.setPassword("");
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", tuser);
		return modelMap;
	}

	// upTuserById
	@PostMapping("/upTuserById")
	public @ResponseBody ModelMap updateById(@RequestBody TUser tUser, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		String p = tUser.getPassword2();
		System.out.println("p:" + p);
		String password = Md5Util.getMd5String(p).toLowerCase();
		tUser.setPassword(password);
		TUser addAnaUpdate = tUserServiceImpl.AddAnaUpdate(tUser);
		
		// 日志
		HttpSession session = request.getSession();
		TUser tuser = (TUser) session.getAttribute("tUser");
		OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
				"修改了ID为" + addAnaUpdate.getId() + "的后台管理", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
		operationLogServiceImpl.AddAnaUpdate(ot);
		
		modelMap.put("message", "修改成功");
		return modelMap;
	}

	// ispassword
	@GetMapping("/ispassword")
	public @ResponseBody ModelMap ispassword(@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "isUser", required = false) String isUser) {
		ModelMap modelMap = new ModelMap();
		// System.out.println("isUser:"+isUser+",password:"+password);
		TUser login = tUserServiceImpl.login(isUser, Md5Util.getMd5String(password).toLowerCase());
		if (login != null) {// 有
			modelMap.put("message", true);
		} else {// 无
			modelMap.put("message", false);
		}
		return modelMap;
	}

	// isUserName
	@GetMapping("/isUserName")
	public @ResponseBody ModelMap isUserName(@RequestParam(value = "isUser", required = false) String isUser,
			@RequestParam(value = "user", required = false) String user) {
		ModelMap modelMap = new ModelMap();
		if (isUser.equals(user)) {
			// TUser login = tUserServiceImpl.login(user, null);
			modelMap.put("message", false);
		} else {// 不相同
			TUser login = tUserServiceImpl.login(user, null);
			if (login != null) {// 有
				modelMap.put("message", true);
			} else {// 无
				modelMap.put("message", false);
			}
		}
		return modelMap;
	}

	private Integer getWinAndLose(int profits) {
		List<Roles> rolesList = new ArrayList<Roles>();
		List<RoundRecord> newTimeUser = roundRecordServiceImpl.getNewTimeUser();
		for (RoundRecord roundRecord : newTimeUser) {
			List<Roles> roles = roundRecord.getRoles();
			for (Roles r : roles) {
				rolesList.add(r);
			}
		}

		// 进行升序排列
		Collections.sort(rolesList, new Comparator<Roles>() {
			public int compare(Roles o1, Roles o2) {
				return o2.getUserid().compareTo(o1.getUserid());// 升序
			}
		});

		List<Roles> repeatAddRoles = ListRepeatAdd.repeatAddRoles(rolesList);
		// 输赢集合
		List<WinAndLose> list = new ArrayList<WinAndLose>();
		for (Roles roles : repeatAddRoles) {
			if (profits > 0) {
				if (roles.getScore() > profits) {
					String userid = roles.getUserid();
					Integer score = roles.getScore();
					ColUser colUser = userServiceImpl.findById(userid, new ColUser());
					if (colUser != null) {
						WinAndLose userAndRecode = new WinAndLose(userid, colUser.getNickname(), score,
								colUser.getCoin());
						list.add(userAndRecode);
					}

				}
			} else if (profits < 0) {
				if (roles.getScore() < profits) {
					String userid = roles.getUserid();
					Integer score = roles.getScore();
					ColUser colUser = userServiceImpl.findById(userid, new ColUser());
					if (colUser != null) {
						WinAndLose userAndRecode = new WinAndLose(userid, colUser.getNickname(), score,
								colUser.getCoin());
						list.add(userAndRecode);
					}
				}
			} else {
				if (roles.getScore() == profits) {
					String userid = roles.getUserid();
					Integer score = roles.getScore();
					ColUser colUser = userServiceImpl.findById(userid, new ColUser());
					if (colUser != null) {
						WinAndLose userAndRecode = new WinAndLose(userid, colUser.getNickname(), score,
								colUser.getCoin());
						list.add(userAndRecode);
					}
				}
			}
		}

		int size = list.size();
		return size;
	}

	// 首页
	@RequiresPermissions("page.list")
	@GetMapping("/index")
	public @ResponseBody ModelMap index(HttpServletRequest request) {
		// 代理
		Integer aTodayNumber = userServiceImpl.getAgentNumber(0, 1, 1, 0);
		Integer ayesterday = userServiceImpl.getAgentNumber(-1, 0, 1, 0);
		Integer aThisMonth = userServiceImpl.getAgentNumber(0, 1, 1, 1);
		Integer aCount = userServiceImpl.getAgentNumber(null, null, 1, null);
		// 玩家
		Integer pTodayNumber = userServiceImpl.getDayAgentNumber(0, 1);
		Integer pyesterday = userServiceImpl.getYesterdayAgentNumber(-1, 0);
		Integer pThisMonth = userServiceImpl.getThisMonthAgentNumber();
		Integer pCount = userServiceImpl.count(new ColUser(), null);
		// 订单的
		Map<String, Object> order = new HashMap<String, Object>();
		order.put("result", 0);
		Integer oCount = orderServiceImpl.getToDayOrderNumber(order);
		Double toDayOrderMoney = orderServiceImpl.getToDayOrderMoney(order);
		Double yesterDayOrderMoney = orderServiceImpl.getYesterDayOrderMoney(order);
		Double sevenDayOrderMoney = orderServiceImpl.getSevenDayOrderMoney(order);

		// 新创建订单,完成
		Integer orderCount = orderServiceImpl.getCount(0);
		// 未完成订单
		Integer onOrderCount = orderServiceImpl.getCount(1);
		// 赢一千
		Integer winThousand = getWinAndLose(1000);
		// 赢一万
		Integer winTenThousand = getWinAndLose(10000);
		// 输一千
		Integer loseThousand = getWinAndLose(-1000);
		// 输一万
		Integer loseTenThousand = getWinAndLose(-10000);
		// 持平
		Integer flat = getWinAndLose(0);

		// 代理未提取的收益
		long unextracted = 0;
		List<ColUser> agent = userServiceImpl.getAgent("0");
		for (ColUser colUser : agent) {
			Integer profit = colUser.getProfit();
			unextracted += profit;
		}

		// 玩家金豆总和
		long allCoin = 0;
		List<ColUser> p = userServiceImpl.getAgent(null);
		for (ColUser colUser : p) {
			long coin = colUser.getCoin();
			allCoin += coin;
		}

		Date time = new Date();
		// 抽水,今天
		long todayPump = 0;
		List<LogCoin> cdate = coinServiceImpl.getTimePump(0, 1, 48, time);
		for (LogCoin logCoin : cdate) {
			Integer num = Math.abs(logCoin.getNum());
			todayPump += num;
		}
		// 抽水,昨天
		long yesterdayPump = 0;
		List<LogCoin> cdate2 = coinServiceImpl.getTimePump(-1, 0, 48, time);
		for (LogCoin logCoin : cdate2) {
			Integer num = Math.abs(logCoin.getNum());
			yesterdayPump += num;
		}

		// 机器人今日营收
		long robotRevenueToday = 0;
		List<RoomResultRecord> timeRevenue = roomResultRecordServiceImpl.getTimeRevenue(0, 1, time,null,null);
		for (RoomResultRecord roleRecord : timeRevenue) {
			String userid = roleRecord.getUserid();
			ColUser colUser = userServiceImpl.findById(userid, new ColUser());
			if (colUser != null) {
				boolean robot = colUser.isRobot();
				if (robot) {
					Integer score = roleRecord.getScore();
					robotRevenueToday += score;
				}
			}
		}

		// 机器人昨日营收
		long robotRevenueYesterday = 0;
		List<RoomResultRecord> timeRevenue2 = roomResultRecordServiceImpl.getTimeRevenue(-1, 0, time,null,null);
		for (RoomResultRecord roleRecord : timeRevenue2) {
			String userid = roleRecord.getUserid();
			ColUser colUser = userServiceImpl.findById(userid, new ColUser());
			if (colUser != null) {
				boolean robot = colUser.isRobot();
				if (robot) {
					Integer score = roleRecord.getScore();
					robotRevenueYesterday += score;
				}
			}
		}

//		Date time = new Date();
		// 服务器
		List<OrderWeekNumber> weekList = new ArrayList<OrderWeekNumber>();
		for (int i = 0; i < 8; i++) {
			OrderWeekNumber orderNumber = orderServiceImpl.getOrderNumber(0, time, i - 7, i - 6);
			weekList.add(orderNumber);
		}
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList<Map>();
		for (OrderWeekNumber orderWeekNumber : weekList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("日期", orderWeekNumber.getWeek());
			m.put("订单数", orderWeekNumber.getNumber());
//			Double money = orderWeekNumber.getMoney();
//			Double money2 = money/(double) 100;
			m.put("订单金额", orderWeekNumber.getMoney());
			list.add(m);
		}
		String[] key = { "日期", "订单数", "订单金额" };

		ModelMap modelMap = new ModelMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("robotRevenueToday", robotRevenueToday);
		map.put("robotRevenueYesterday", robotRevenueYesterday);
		map.put("unextracted", unextracted);
		map.put("allCoin", allCoin);
		map.put("todayPump", todayPump);
		// 代理的
		map.put("yesterdayPump", yesterdayPump);
		map.put("aTodayNumber", aTodayNumber);
		map.put("ayesterday", ayesterday);
		map.put("aThisMonth", aThisMonth);
		map.put("aCount", aCount);
		// 玩家的
		map.put("pTodayNumber", pTodayNumber);
		map.put("pyesterday", pyesterday);
		map.put("pThisMonth", pThisMonth);
		map.put("pCount", pCount);
		// 订单的
		map.put("oCount", oCount);
		map.put("toDayOrderMoney", toDayOrderMoney);
		map.put("yesterDayOrderMoney", yesterDayOrderMoney);
		map.put("sevenDayOrderMoney", sevenDayOrderMoney);

		map.put("orderCount", orderCount);
		map.put("onOrderCount", onOrderCount);
		map.put("winThousand", winThousand);
		map.put("winTenThousand", winTenThousand);
		map.put("loseThousand", loseThousand);
		map.put("loseTenThousand", loseTenThousand);
		map.put("flat", flat);
		map.put("rows", list);
		map.put("columns", key);
		modelMap.put("message", map);
		return modelMap;
	}

	// sevenDaysRegistered
	@GetMapping("/sevenDaysRegistered")
	public @ResponseBody ModelMap sevenDaysRegistered(HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		Date time = new Date();
		// 服务器
		List<OrderWeekNumber> weekList = new ArrayList<OrderWeekNumber>();
		for (int i = 0; i < 8; i++) {
			OrderWeekNumber orderNumber = orderServiceImpl.getOrderNumber(0, time, i - 7, i - 6);
			weekList.add(orderNumber);
		}
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList<Map>();
		for (OrderWeekNumber orderWeekNumber : weekList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("日期", orderWeekNumber.getWeek());
			m.put("订单数", orderWeekNumber.getNumber());
			m.put("订单金额", orderWeekNumber.getMoney());
			list.add(m);
		}
		String[] key = { "日期", "订单数", "订单金额" };
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("columns", key);
		modelMap.put("message", map);
		return modelMap;
	}

	// lastWeekRegistered
	@GetMapping("/lastWeekRegistered")
	public @ResponseBody ModelMap lastWeekRegistered(HttpServletRequest request) {
		String mondayOfThisWeek = DateFormat.getMondayOfThisWeek();

		Date time = null;
		try {
			time = new SimpleDateFormat("yyyy-MM-dd").parse(mondayOfThisWeek);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 服务器
		List<OrderWeekNumber> weekList = new ArrayList<OrderWeekNumber>();
		for (int i = 0; i < 8; i++) {
			OrderWeekNumber orderNumber = orderServiceImpl.getOrderNumber(0, time, i - 7, i - 6);
			weekList.add(orderNumber);
		}
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList<Map>();
		for (OrderWeekNumber orderWeekNumber : weekList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("日期", orderWeekNumber.getWeek());
			m.put("订单数", orderWeekNumber.getNumber());
			m.put("订单金额", orderWeekNumber.getMoney());
			list.add(m);
		}
		String[] key = { "日期", "订单数", "订单金额" };
		
		ModelMap modelMap = new ModelMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("columns", key);
		modelMap.put("message", map);
		return modelMap;
	}

	// thisMonthRegistered
	@GetMapping("/thisMonthRegistered")
	public @ResponseBody ModelMap thisMonthRegistered(HttpServletRequest request) {
		// modelMap.put("weekList", weekList);
		String lastDayOfLastMonth = DateFormat.lastDayOfLastMonth();
		// System.out.println("l:"+lastDayOfLastMonth);
		
		Date time = null;
		try {
			time = new SimpleDateFormat("yyyy-MM-dd").parse(lastDayOfLastMonth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer lastMonthDay = DateFormat.lastMonthDay();
		// System.out.println("ll:"+lastMonthDay);
		List<OrderWeekNumber> weekList = new ArrayList<OrderWeekNumber>();
		for (int i = 1; i <= lastMonthDay; i++) {
			OrderWeekNumber orderNumberi = orderServiceImpl
					.getOrderNumber(0, time, i - lastMonthDay, i + 1
							- lastMonthDay);
			weekList.add(orderNumberi);
		}
		
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList<Map>();
		for (OrderWeekNumber orderWeekNumber : weekList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("日期", orderWeekNumber.getWeek());
			m.put("订单数", orderWeekNumber.getNumber());
			m.put("订单金额", orderWeekNumber.getMoney());
			list.add(m);
		}
		String[] key = { "日期", "订单数", "订单金额" };
		
		ModelMap modelMap = new ModelMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("columns", key);
		modelMap.put("message", map);
		return modelMap;
	}
}
