package com.happyProject.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happyProject.admin.model.OrderWeekNumber;
import com.happyProject.admin.service.impl.OnlinkServiceImpl;

@RestController
@RequestMapping("/onlink")
@CrossOrigin
public class OnlinkController {
	@Resource
	private OnlinkServiceImpl OnlinkService;

	@RequiresPermissions("online.list")
	@GetMapping("/onlink")
	public @ResponseBody ModelMap onlink(@RequestParam(value = "last", required = false) Integer last,
			@RequestParam(value = "end", required = false) Integer end) {
		List<OrderWeekNumber> findByCtime = OnlinkService.findByCtime(last, end);
		@SuppressWarnings("rawtypes")
		List<Map> list =new ArrayList<Map>();
		for (OrderWeekNumber orderWeekNumber : findByCtime) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("日期", orderWeekNumber.getWeek());
			m.put("人数", orderWeekNumber.getNumber());
			list.add(m);
		}
		String[] key = { "日期", "人数"};

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("columns", key);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}
}
