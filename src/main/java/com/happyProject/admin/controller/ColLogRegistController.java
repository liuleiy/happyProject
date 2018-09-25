package com.happyProject.admin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happyProject.admin.model.OrderWeekNumber;
import com.happyProject.admin.service.impl.ColLogRegistServiceImpl;
import com.happyProject.admin.utlis.DateFormat;

@RestController
@RequestMapping("/colLogRegist")
@CrossOrigin
public class ColLogRegistController {
	@Resource
	private ColLogRegistServiceImpl colLogRegistService;

	// 七天
	@RequiresPermissions("registered.list")
	@RequestMapping("/sevenDaysRegistered")
	public @ResponseBody ModelMap sevenDaysRegistered() {
		Date time = new Date();
		List<OrderWeekNumber> weekList = new ArrayList<OrderWeekNumber>();
		for (int i = 0; i <= 7; i++) {
			OrderWeekNumber orderNumber = colLogRegistService.getOrderNumber(time, i - 7, i + 1 - 7);
			weekList.add(orderNumber);
		}
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList<Map>();
		for (OrderWeekNumber orderWeekNumber : weekList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("日期", orderWeekNumber.getWeek());
			m.put("人数", orderWeekNumber.getNumber());
			list.add(m);
		}
		String[] key = { "日期", "人数" };

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("columns", key);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}

	// 上周
	@RequiresPermissions("registered.list")
	@GetMapping("/lastWeekRegistered")
	public @ResponseBody ModelMap lastWeekRegistered() {
		String mondayOfThisWeek = DateFormat.getMondayOfThisWeek();
		Date time = null;
		try {
			time = new SimpleDateFormat("yyyy-MM-dd").parse(mondayOfThisWeek);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<OrderWeekNumber> weekList = new ArrayList<OrderWeekNumber>();
		for (int i = 0; i <= 7; i++) {
			OrderWeekNumber orderNumber = colLogRegistService.getOrderNumber(time, i - 7, i + 1 - 7);
			weekList.add(orderNumber);
		}
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList<Map>();
		for (OrderWeekNumber orderWeekNumber : weekList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("日期", orderWeekNumber.getWeek());
			m.put("人数", orderWeekNumber.getNumber());
			list.add(m);
		}
		String[] key = { "日期", "人数" };

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("columns", key);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}

	// 注册统计,上个月
	@RequiresPermissions("registered.list")
	@RequestMapping("/thisMonthRegistered")
	public @ResponseBody ModelMap thisMonthRegistered() {
		String lastDayOfLastMonth = DateFormat.lastDayOfLastMonth();
		Date time = null;
		try {
			time = new SimpleDateFormat("yyyy-MM-dd").parse(lastDayOfLastMonth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer lastMonthDay = DateFormat.lastMonthDay();
		List<OrderWeekNumber> weekList = new ArrayList<OrderWeekNumber>();
		for (int i = 1; i <= lastMonthDay; i++) {
			OrderWeekNumber orderNumber = colLogRegistService.getOrderNumber(time, i - lastMonthDay,
					i + 1 - lastMonthDay);
			weekList.add(orderNumber);
		}
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList<Map>();
		for (OrderWeekNumber orderWeekNumber : weekList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("日期", orderWeekNumber.getWeek());
			m.put("人数", orderWeekNumber.getNumber());
			list.add(m);
		}
		String[] key = { "日期", "人数" };

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("columns", key);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}

}