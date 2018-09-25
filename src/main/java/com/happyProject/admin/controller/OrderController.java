package com.happyProject.admin.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.happyProject.admin.model.Condition;
import com.happyProject.admin.model.Goods;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.Order;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.impl.ColUserServiceImpl;
import com.happyProject.admin.service.impl.GoodsServiceImpl;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.service.impl.OrderServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
	@Resource
	private OrderServiceImpl orderServiceImpl;
	@Resource
	private GoodsServiceImpl goodsServiceImpl;
	@Resource
	private ColUserServiceImpl colUserServiceImpl;

	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;
	
	@RequiresPermissions("tradeRecord.list")
	@PostMapping("/order")
	public @ResponseBody ModelMap goods(@RequestBody Condition condition) {
		System.out.println("order");
		String userid = condition.getUserid();
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		Integer result = condition.getResult();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		PageBean<Order> pageBean = orderServiceImpl.findByDataAndCount(userid, currentPage, pageSize, startTime,
				endTime, result);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}

	@RequiresPermissions("tradeRecord.del")
	@PostMapping("/orderDel")
	public @ResponseBody ModelMap orderDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				Order order = new Order();
				order.setId(id);
				orderServiceImpl.remove(order);
				
				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的订单", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

	@RequiresPermissions("tradeRecord.details")
	@GetMapping("/orderDetails")
	public @ResponseBody ModelMap orderDetails(@RequestParam(value = "userid", required = false) String userid,
			@RequestParam(value = "itemid", required = false) String itemid,
			@RequestParam(value = "iid", required = false) String iid) {
		Goods goods = goodsServiceImpl.findById(itemid, new Goods());
		Order order = orderServiceImpl.findById(iid, new Order());
		ColUser colUser = colUserServiceImpl.findById(userid, new ColUser());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goods", goods);
		map.put("colUser", colUser);
		map.put("order", order);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}

}
