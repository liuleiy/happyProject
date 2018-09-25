package com.happyProject.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.chuanglan.sms.response.SmsBalanceResponse;
import com.happyProject.admin.model.ColUser;
import com.happyProject.admin.model.Condition;
import com.happyProject.admin.model.Label;
import com.happyProject.admin.model.LogCoin;
import com.happyProject.admin.model.LoginLog;
import com.happyProject.admin.model.Notice;
import com.happyProject.admin.model.Notices;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.Order;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.PayCurrency;
import com.happyProject.admin.model.Roles;
import com.happyProject.admin.model.RoundRecord;
import com.happyProject.admin.model.SmsMessage;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.model.WinAndLose;
import com.happyProject.admin.service.impl.ColUserServiceImpl;
import com.happyProject.admin.service.impl.LabelServiceImpl;
import com.happyProject.admin.service.impl.LogCoinServiceImpl;
import com.happyProject.admin.service.impl.LoginLogServiceImpl;
import com.happyProject.admin.service.impl.NoticesServiceImpl;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.service.impl.OrderServiceImpl;
import com.happyProject.admin.service.impl.RoundRecordServiceImpl;
import com.happyProject.admin.service.impl.SequenceServiceImpl;
import com.happyProject.admin.service.impl.SmsMessageServiceimpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.ListRepeatAdd;
import com.happyProject.admin.utlis.SendMages;
import com.happyProject.admin.utlis.SendTheRequest;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/colUser")
@CrossOrigin
public class ColUserController {
	@Resource
	private ColUserServiceImpl colUserService;
	@Resource
	private OrderServiceImpl orderService;
	@Resource
	private LabelServiceImpl labelService;
	@Resource
	private LoginLogServiceImpl loginLogServiceImpl;
	@Resource
	private LogCoinServiceImpl coinServiceImpl;
	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;
	@Resource
	private SmsMessageServiceimpl smsMessageServiceimpl;
	// 每局结算详情记录
	@Resource
	private RoundRecordServiceImpl roundRecordServiceImpl;
	// 公告
	@Resource
	private NoticesServiceImpl noticesServiceImpl;
	@Resource
	private SequenceServiceImpl sequenceServiceImpl;

//	@RequiresPermissions("user.list")
	@RequiresPermissions(value = { "user.list", "agent.list" })
	@PostMapping("/colUser")
	public @ResponseBody ModelMap goods(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		String iid = condition.getIid();
		String nickname = condition.getNickname();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		Integer agent_state = condition.getAgent_state();
		String agent = condition.getAgent();

		Integer rtypeValue = condition.getRtypeValue();
		String start_profit = condition.getStart_profit();
		String last_profit = condition.getLast_profit();
		String start_bring_profit = condition.getStart_bring_profit();
		String last_bring_profit = condition.getLast_bring_profit();
		String arrangement = condition.getArrangement();
		PageBean<ColUser> pageBean = colUserService.findByDataAndCount(iid, nickname, agent_state, currentPage,
				pageSize, startTime, endTime, agent, start_profit, last_profit, start_bring_profit, last_bring_profit,
				rtypeValue, arrangement);
		List<ColUser> data = pageBean.getData();
		List<ColUser> dateMap = new ArrayList<ColUser>();
		for (ColUser colUser : data) {
			dateMap.add(colUser);
		}

		// 设置标签
		for (ColUser colUser : dateMap) {
			String userid = colUser.getId();
			PageBean<Order> orderList = orderService.findByDataAndCount(userid, null, null, null, null, 0);
			Integer allRow = orderList.getAllRow();
			// 一个user的所有有效订单的总金额
			Double count = 0d;
			if (allRow > 0) {
				List<Order> data2 = orderList.getData();
				for (Order order : data2) {
					count += order.getMoney();
				}
				List<Label> ladels = new ArrayList<Label>();
				// 所有标签的数据
				List<? extends Object> all = labelService.getAll(new Label());
				for (Object a : all) {
					ladels.add((Label) a);
				}
				// Collections.sort(humans, Comparator.comparing(Human::getName));
				Collections.sort(ladels, Comparator.comparing(Label::getTmoney));
				String name = "无";
				for (Label label : ladels) {
					// 标签达到所需要的金额
					Double tmoney = label.getTmoney()*100;
					// 标签所需要达到的订单数
					Integer tcount = label.getTcount();
					if (count > tmoney && allRow <= tcount) {
						name = label.getLabelname();
					}
				}
				colUser.setLadelName(name);
			}

		}
		pageBean.setData(dateMap);

		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}

	@RequiresPermissions("user.del")
	@PostMapping("/colUserDel")
	public @ResponseBody ModelMap colUserDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				ColUser colUser = new ColUser();
				colUser.setId(id);
				colUserService.remove(colUser);

				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的用户", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

	@RequiresPermissions("user.details")
	@GetMapping("/colUserDetails")
	public @ResponseBody ModelMap colUserDetails(@RequestParam(value = "iid", required = false) String iid,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		ColUser colUser = colUserService.findById(iid, new ColUser());
		PageBean<Order> pageBean = orderService.findByDataAndCount(iid, currentPage, pageSize, null, null, null);
		// 登录次数
		Integer total = loginLogServiceImpl.getTotal(iid);
		// 充值金额
		int totalMoneyInt = 0;
		PageBean<Order> po = orderService.findByDataAndCount(iid, null, null, null, null, 0);
		List<Order> data = po.getData();
		for (Order order : data) {
			Double money = order.getMoney();
			totalMoneyInt += (money * 100);
		}
		Double totalMoney = 0d;
		if (totalMoneyInt != 0) {
			totalMoney = (double) (totalMoneyInt / 100);
		}

		// 输赢
		// 输赢平的局数
		int winNumber = 0;
		int lostNumber = 0;
		int pingNumber = 0;
		PageBean<RoundRecord> pb = roundRecordServiceImpl.findByDataAndCount(null, null, null, null, null);
		List<RoundRecord> list = pb.getData();
		for (RoundRecord roundRecord : list) {
			List<Roles> roles = roundRecord.getRoles();
			for (Roles roles2 : roles) {
				String userid = roles2.getUserid();
				if (iid.equals(userid)) {
					// score
					Integer score = roles2.getScore();
					if (score > 0) {
						winNumber++;
					} else if (score < 0) {
						lostNumber++;
					} else {
						pingNumber++;
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("colUser", colUser);// 用户的数据
		map.put("pageBean", pageBean);// 单个订单的数据
		map.put("total", total);// 登录次数
		map.put("totalMoney", totalMoney);// 订单有效金额
		map.put("winNumber", winNumber);
		map.put("lostNumber", lostNumber);
		map.put("pingNumber", pingNumber);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}

	// loginLog
	@GetMapping("/loginLog")
	public @ResponseBody ModelMap loginLog(@RequestParam(value = "iid", required = false) String iid,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		System.out.println("loginLog");
		PageBean<LoginLog> pagebean = loginLogServiceImpl.findByDataAndCount(currentPage, pageSize, iid);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", pagebean);
		return modelMap;
	}

	// profitsColuser
	@GetMapping("/profitsColuser")
	public @ResponseBody ModelMap profitsColuser(@RequestParam(value = "profits", required = false) Integer profits) {

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
					ColUser colUser = colUserService.findById(userid, new ColUser());
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
					ColUser colUser = colUserService.findById(userid, new ColUser());
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
					ColUser colUser = colUserService.findById(userid, new ColUser());
					if (colUser != null) {
						WinAndLose userAndRecode = new WinAndLose(userid, colUser.getNickname(), score,
								colUser.getCoin());
						list.add(userAndRecode);
					}
				}
			}
		}

		int size = list.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("size", size);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}

	@RequiresPermissions("bankRecord.list")
	@PostMapping("/bankRecord")
	public @ResponseBody ModelMap bankRecord(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		String iid = condition.getIid();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		PageBean<LogCoin> pageBean = coinServiceImpl.findByDataAndCount(iid, currentPage, pageSize, startTime, endTime,
				15);
//		PageBean<ColUser> pageBean = colUserService.findByDataAndCount(iid, nickname, agent_state, currentPage,
//				pageSize, startTime, endTime);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}

	// 赠送金币
	@RequiresPermissions("coin.list")
	@PostMapping("/coinPop")
	public @ResponseBody ModelMap coinPop(@RequestBody Condition condition, HttpServletRequest request) {

		String strCheck = condition.getStrCheck();
		Integer give = condition.getGive();
		String radio = condition.getRadio();
		String name = condition.getName();
		if("true".equals(name)) {//代表删减
			give = 0-give;
		}
		System.out.println("give:"+give);
		String[] split = strCheck.split(",");
		for (String s : split) {
			if (!"".equals(s.trim())) {
				if (radio.equals("金豆")) {// 1
					SendTheRequest.crud(4, 0, "id1", new PayCurrency(s, null, give, null, null, null, null));

					// 日志
					HttpSession session = request.getSession();
					TUser tuser = (TUser) session.getAttribute("tUser");
					OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
							"用户id:" + s + ",增加" + give + "个金豆", new Date());
					operationLogServiceImpl.AddAnaUpdate(ot);
				} else if (radio.equals("金币")) {// 2
					SendTheRequest.crud(4, 0, "id1", new PayCurrency(s, null, null, give, null, null, null));

					// 日志
					HttpSession session = request.getSession();
					TUser tuser = (TUser) session.getAttribute("tUser");
					OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
							"用户id:" + s + ",增加" + give + "个金币", new Date());
					operationLogServiceImpl.AddAnaUpdate(ot);
				}
			}
		}

		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "赠送成功");
		return modelMap;

	}

	// 请求条数,smsNumber
	@PostMapping("/smsNumber")
	public @ResponseBody ModelMap smsNumber() {

		// 全部条数
		String url = "http://smssh1.253.com/msg/balance/json";
		SmsBalanceResponse money = SendMages.getMoney(url);
		String code = money.getCode();
		String balance = money.getBalance();
		// 全部的用户
		List<? extends Object> all = colUserService.getAll(new ColUser());
		int size = all.size();

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		map.put("balance", balance);
		map.put("size", size);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}

	// sendSms,发送短信
	@RequiresPermissions("sms.list")
	@PostMapping("/sendSms")
	public @ResponseBody ModelMap sendSms(@RequestBody Condition condition, HttpServletRequest request) {

		String strCheck = condition.getStrCheck();
		String content = condition.getContent();
		String radio = condition.getRadio();

		String url = "http://smssh1.253.com/msg/send/json";
		content = "【欢斗娱乐】" + content;
		if (radio.equals("全部用户")) {
			String phone = "";
			// String phone = "17707270361,15811837594";
			List<? extends Object> findAll = colUserService.getAll(new ColUser());
			int size = findAll.size();
			int ts = size / 999;
			Integer i = 0;
			while (ts >= 0) {
				Integer entry = 0;
				if (size > 1000) {
					entry = 1000;
				} else {
					entry = size;
				}
				for (; i < entry; i++) {
					ColUser colUser = (ColUser) findAll.get(i);
					String p = colUser.getBank_phone();//getPhone();

					if (p != null && !("".equals(p.trim()))) {//
						phone = (phone + p + ",");
					}
				}

				// 发短信
				SendMages.sendMessage(url, content, phone, "true");
				ts--;

				// 添加sms消息到数据库
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				String publisher = tuser.getUser_name();
				Date ctime = TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss");
				SmsMessage t = new SmsMessage(null, publisher, ctime, size, content);
				smsMessageServiceimpl.AddAnaUpdate(t);
			}
		} else if (radio.equals("选中用户")) {
			if (!("".equals(strCheck.trim()))) {
				String phone = "";
				String[] split = strCheck.split(",");
				for (String id : split) {
					// System.out.println("s:"+id);

					ColUser user = colUserService.findById(id, new ColUser());
					String p = user.getBank_phone();//.getPhone();
					if (p != null && !("".equals(p.trim()))) {
						phone = (phone + p + ",");
					}
					// System.out.println("p:"+user.getPhone());
				}
				// 发短信
				System.out.println("phone:" + phone);
				SendMages.sendMessage(url, content, phone, "true");
				// System.out.println("s:"+sendMessage);

				// 添加sms消息到数据库
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				String publisher = tuser.getUser_name();
				Date ctime = TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss");
				SmsMessage t = new SmsMessage(null, publisher, ctime, split.length, content);
				smsMessageServiceimpl.AddAnaUpdate(t);
			}
		}

		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "发送成功");
		return modelMap;
	}

	// 发送APP消息,sendApp
	@RequiresPermissions("app.list")
	@PostMapping("/sendApp")
	public @ResponseBody ModelMap sendApp(@RequestBody Condition condition, HttpServletRequest request) {
		String strCheck = condition.getStrCheck();
		String content = condition.getContent();
		String radio = condition.getRadio();
		String radioMagess = condition.getRadioMagess();
		Integer rtype = 1;
		if (radioMagess.equals("公告")) {
			rtype = 1;
		} else if (radioMagess.equals("广播")) {
			rtype = 2;
		} else if (radioMagess.equals("系统")) {
			rtype = 3;
		}

		// 创建时间
		Date time = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
		s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String format2 = s.format(time);
		// 过期时间
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		Date date = cal.getTime();
		SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		s2.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String format1 = s2.format(date);
		System.out.println("sele:" + radio);
		if (radio.equals("全部用户")) {// 全部,有问题
			String nextId = sequenceServiceImpl.nextId("notice");
			Notice notice = new Notice(nextId, "", rtype, 0, 0, 0, 1, 0, content, format1, format2);
			SendTheRequest.add(2, 0, "", notice);

			// 公告的数据库操作
			Notices notices = new Notices(nextId, "", rtype, 0, 0, 0, 1, 0, content,
					TrunTime.setTime(date, "yyyy-MM-dd HH:mm:ss"), TrunTime.setTime(time, "yyyy-MM-dd HH:mm:ss"));
			noticesServiceImpl.AddAnaUpdate(notices);

			// 公告的ID数据库操作
			sequenceServiceImpl.nextOne("notice", nextId);

		} else if (radio.equals("选中用户") && strCheck.length() > 0) {// 选中
			String nextId = sequenceServiceImpl.nextId("notice");
			Notice notice = new Notice(nextId, strCheck, rtype, 0, 0, 0, 1, 0, content, format1, format2);
			SendTheRequest.add(2, 0, "", notice);

			// 公告的数据库操作
			Notices notices = new Notices(nextId, "", rtype, 0, 0, 0, 1, 0, content,
					TrunTime.setTime(date, "yyyy-MM-dd HH:mm:ss"), TrunTime.setTime(time, "yyyy-MM-dd HH:mm:ss"));
			noticesServiceImpl.AddAnaUpdate(notices);

			// 公告的ID数据库操作
			sequenceServiceImpl.nextOne("notice", nextId);
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "发送成功");
		return modelMap;
	}

	// agentSmsNumber,代理的条数
	@PostMapping("/agentSmsNumber")
	public @ResponseBody ModelMap agentSmsNumber() {

		// 全部条数
		String url = "http://smssh1.253.com/msg/balance/json";
		SmsBalanceResponse money = SendMages.getMoney(url);
		String code = money.getCode();
		String balance = money.getBalance();
		// 全部的用户
		List<? extends Object> all = colUserService.findAngentAll(1);
		int size = all.size();

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		map.put("balance", balance);
		map.put("size", size);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}

	// agentSendSms
	@RequiresPermissions("sms.list")
	@PostMapping("/agentSendSms")
	public @ResponseBody ModelMap agentSendSms(@RequestBody Condition condition, HttpServletRequest request) {

		String strCheck = condition.getStrCheck();
		String content = condition.getContent();
		String radio = condition.getRadio();

		String url = "http://smssh1.253.com/msg/send/json";
		content = "【欢斗娱乐】" + content;
		if (radio.equals("全部用户")) {// 全部
			String phone = "";
			// String phone = "17707270361,15811837594";
			// List<? extends Object> findAll = colUserServiceImpl
			// .findAll(new ColUser());
			List<ColUser> findAll = colUserService.findAngentAll(1);
			int size = findAll.size();
			int ts = size / 999;
			Integer i = 0;
			while (ts >= 0) {
				Integer entry = 0;
				if (size > 1000) {
					entry = 1000;
				} else {
					entry = size;
				}
				for (; i < entry; i++) {
					ColUser colUser = (ColUser) findAll.get(i);
					String p = colUser.getBank_phone();//.getPhone();

					if (p != null && !("".equals(p.trim()))) {//
						phone = (phone + p + ",");
					}
				}

				// 发短信
				SendMages.sendMessage(url, content, phone, "true");
				ts--;

				// 添加sms消息到数据库
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				String publisher = tuser.getUser_name();
				Date ctime = TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss");
				SmsMessage t = new SmsMessage(null, publisher, ctime, size, content);
				smsMessageServiceimpl.AddAnaUpdate(t);
			}

		} else if (radio.equals("选中用户")) {// 选中
			if (!("".equals(strCheck.trim()))) {
				String phone = "";
				String[] split = strCheck.split(",");
				for (String id : split) {
					// System.out.println("s:"+id);

					ColUser user = colUserService.findById(id, new ColUser());
					String p = user.getBank_phone();//.getPhone();
					if (p != null && !("".equals(p.trim()))) {
						phone = (phone + p + ",");
					}
					// System.out.println("p:"+user.getPhone());
				}
				// 发短信
				System.out.println("phone:" + phone);
				SendMages.sendMessage(url, content, phone, "true");
				// System.out.println("s:"+sendMessage);

				// 添加sms消息到数据库
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				String publisher = tuser.getUser_name();
				Date ctime = TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss");
				SmsMessage t = new SmsMessage(null, publisher, ctime, split.length, content);
				smsMessageServiceimpl.AddAnaUpdate(t);
			}
		}

		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "发送成功");
		return modelMap;
	}

	// agentSendApp
	@RequiresPermissions("app.list")
	@PostMapping("/agentSendApp")
	public @ResponseBody ModelMap agentSendApp(@RequestBody Condition condition, HttpServletRequest request) {

		String strCheck = condition.getStrCheck();
		String content = condition.getContent();
		String radio = condition.getRadio();
		String radioMagess = condition.getRadioMagess();
		Integer rtype = 1;
		if (radioMagess.equals("公告")) {
			rtype = 1;
		} else if (radioMagess.equals("广播")) {
			rtype = 2;
		} else if (radioMagess.equals("系统")) {
			rtype = 3;
		}

		// 创建时间
		Date time = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
		s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String format2 = s.format(time);
		// 过期时间
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		Date date = cal.getTime();
		String format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date);
		System.out.println("sele:" + radio);
		if (radio.equals("全部用户")) {// 全部,有问题
			String id = "";
			// 全部代理
			List<ColUser> findAll = colUserService.findAngentAll(1);
			if (findAll != null && findAll.size() > 0) {
				for (ColUser colUser : findAll) {
					String cid = colUser.getId();
					id += (cid + ",");
				}
			}

			String nextId = sequenceServiceImpl.nextId("notice");
			Notice notice = new Notice(nextId, id, rtype, 0, 0, 0, 1, 0, content, format1, format2);
			SendTheRequest.add(2, 0, "", notice);

			// 公告的数据库操作
			Notices notices = new Notices(nextId, id, rtype, 0, 0, 0, 1, 0, content, date, time);
			noticesServiceImpl.AddAnaUpdate(notices);

			// 公告的ID数据库操作
			sequenceServiceImpl.nextOne("notice", nextId);

		} else if (radio.equals("选中用户") && strCheck.length() > 0) {// 选中
			String nextId = sequenceServiceImpl.nextId("notice");
			Notice notice = new Notice(nextId, strCheck, rtype, 0, 0, 0, 1, 0, content, format1, format2);
			SendTheRequest.add(2, 0, "", notice);

			// 公告的数据库操作
			Notices notices = new Notices(nextId, strCheck, rtype, 0, 0, 0, 1, 0, content, date, time);
			noticesServiceImpl.AddAnaUpdate(notices);

			// 公告的ID数据库操作
			sequenceServiceImpl.nextOne("notice", nextId);
		}

		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "发送成功");
		return modelMap;
	}

	// 合伙人
	@PostMapping("/setPartner")
	public @ResponseBody ModelMap setPartner(@RequestBody Condition condition) {
		String iid = condition.getIid();
		ColUser colUser = colUserService.findById(iid, new ColUser());
		String agent = colUser.getAgent();
		Integer agent_state = colUser.getAgent_state();
		Integer profit_rate_sum = colUser.getProfit_rate_sum();
		Integer agent_level = colUser.getAgent_level();
		ModelMap modelMap = new ModelMap();
		if(!"".equals(agent)&&agent_state==1&&profit_rate_sum==0){//普通代理,agent !=""
			//先清空agent
			SendTheRequest.setAgent(iid,"");
			SendTheRequest.setPartner(iid);
			SendTheRequest.setRate(iid, 23);
			modelMap.put("message", "设置成功");
		}else if(!"".equals(agent)&&agent_state==1&&profit_rate_sum!=0){//大代理
			//先清空agent
			SendTheRequest.setAgent(iid,"");
			SendTheRequest.setPartner(iid);
			SendTheRequest.setRate(iid, 23);
			modelMap.put("message", "设置成功");
		}
		else if("".equals(agent)&&agent_state==1&&agent_level==1){//合伙人
			modelMap.put("message", "已经是合伙人");
		}else if("".equals(agent)&&(agent_state!=1||agent_level==0)){//玩家
			SendTheRequest.setAgent(iid,"");
			SendTheRequest.setPartner(iid);
			SendTheRequest.setRate(iid, 23);
			modelMap.put("message", "设置成功");
		}
		/*if ("".equals(agent)) {// 玩家
			SendTheRequest.setPartner(iid);
			modelMap.put("message", "设置成功");
		} else {// 代理
				// 先清空agent
			SendTheRequest.setAgent(iid, "");
			SendTheRequest.setPartner(iid);
			modelMap.put("message", "设置成功");
		}*/
		return modelMap;
	}

	// 解除绑定
	@PostMapping("/relieve")
	public @ResponseBody ModelMap relieve(@RequestBody Condition condition) {
		String iid = condition.getIid();
		ModelMap modelMap = new ModelMap();
		SendTheRequest.setAgent(iid, "");
		modelMap.put("message", "设置成功");
		return modelMap;
	}

	// 绑定
	@PostMapping("/binding")
	public @ResponseBody ModelMap binding(@RequestBody Condition condition) {
		String check = condition.getCheck();
		String agentID = condition.getAgentID();
		ModelMap modelMap = new ModelMap();
		SendTheRequest.setAgent(check, agentID);
		modelMap.put("message", "设置成功");

		return modelMap;
	}
	//设置比例
	@RequestMapping("/setRate")
	public @ResponseBody ModelMap setRate(@RequestBody Condition condition) {
		String check = condition.getCheck();//id
		String agentID = condition.getAgentID();//比例
		int agentInt = Integer.parseInt(agentID);
		ModelMap modelMap = new ModelMap();
		if(agentInt>=0&&agentInt<=23){
			Map<String, Object> map = new HashMap<String, Object>();
			//SendTheRequest.setRate(useriid, rate);
			map.put("profit_rate_sum", agentInt);
			colUserService.updateById(check, map , new ColUser());
			modelMap.put("message", "设置成功");
		}else{
			System.out.println("不符合比例设置");
			modelMap.put("message", "不符合比例设置");
		}
		//modelMap.put("message", "设置成功");

		return modelMap;
	}
}
