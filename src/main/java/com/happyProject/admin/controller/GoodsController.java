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
import com.happyProject.admin.model.Goods;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.ShopSend;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.impl.GoodsServiceImpl;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.SendTheRequest;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {

	@Resource
	private GoodsServiceImpl serviceImpl;

	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;

//	@RequiresPermissions(value = { "as:as" })
	@RequiresPermissions("shop.list")
	@PostMapping("/goods")
	public @ResponseBody ModelMap goods(@RequestBody Condition condition) {
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		String name = condition.getName();
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		Integer del = condition.getDel();
		ModelMap modelMap = new ModelMap();
		PageBean<Goods> pageBean = serviceImpl.findByDataAndCount(name, currentPage, pageSize, startTime, endTime, del);
		modelMap.put("data", pageBean);
		return modelMap;
	}
	
	@RequiresPermissions("shop.del")
	@PostMapping("/goodsDel")
	public @ResponseBody ModelMap goodsDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				Goods goods = new Goods();
				goods.setId(id);
				serviceImpl.remove(goods);
				
				// 删除的消息告知游戏
				String id2 = goods.getId();
				Integer status2 = goods.getStatus();
				Integer propid2 = goods.getPropid();
				Integer payway2 = goods.getPayway();
				Long number2 = goods.getNumber();
				Double price2 = goods.getPrice();
				String name2 = goods.getName();
				String info2 = goods.getInfo();
				Integer del2 = goods.getDel();
				Date etime2 = goods.getEtime();
				SimpleDateFormat s0 = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
				s0.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
				String format1 = s0.format(etime2);
				Date ctime2 = goods.getCtime();
				SimpleDateFormat s = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
				s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
				String format2 = s.format(ctime2);
				ShopSend shopSend = new ShopSend(id2, status2, propid2, payway2, number2, price2, name2, info2, del2, format1, format2);
				SendTheRequest.delShop(5, 1, id2, shopSend);
				
				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的商品", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

	@RequiresPermissions("shop.remove")
	@PostMapping("/goodsEdit")
	public @ResponseBody ModelMap goodsEdit(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		Integer del = condition.getDel();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("del", del);
				serviceImpl.updateById(id, m, new Goods());

				Goods findById = serviceImpl.findById(id, new Goods());
				// 修改的消息告知游戏
				String id2 = findById.getId();
				Integer status2 = findById.getStatus();
				Integer propid2 = findById.getPropid();
				Integer payway2 = findById.getPayway();
				Long number2 = findById.getNumber();
				Double price2 = findById.getPrice();
				String name2 = findById.getName();
				String info2 = findById.getInfo();
				Integer del2 = findById.getDel();
				Date etime2 = findById.getEtime();
				SimpleDateFormat s0 = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
				s0.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
				String format1 = s0.format(etime2);
				Date ctime2 = findById.getCtime();
				SimpleDateFormat s = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
				s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
				String format2 = s.format(ctime2);
				ShopSend shopSend = new ShopSend(id2, status2, propid2, payway2, number2, price2, name2, info2, del2, format1, format2);
				SendTheRequest.delShop(5, 0, id2, shopSend);
				
				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"修改了ID为" + id + "的商品", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);

			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "修改成功");
		return modelMap;
	}

	@GetMapping("/findById")
	public @ResponseBody ModelMap findById(@RequestParam(value = "id", required = false) String id) {
		Goods goods = serviceImpl.findById(id, new Goods());
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", goods);
		return modelMap;
	}

	@RequiresPermissions("shop.edit")
	@PostMapping("/updateById")
	public @ResponseBody ModelMap updateById(@RequestBody Goods goods, HttpServletRequest request) {
		// System.out.println("g:"+goods);
		Goods addAnaUpdate = serviceImpl.AddAnaUpdate(goods);

		Goods findById = serviceImpl.findById(goods.getId(), new Goods());
		// 修改的消息告知游戏
		String id2 = findById.getId();
		Integer status2 = findById.getStatus();
		Integer propid2 = findById.getPropid();
		Integer payway2 = findById.getPayway();
		Long number2 = findById.getNumber();
		Double price2 = findById.getPrice();
		String name2 = findById.getName();
		String info2 = findById.getInfo();
		Integer del2 = findById.getDel();
		Date etime2 = findById.getEtime();
		SimpleDateFormat s0 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
		s0.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String format1 = s0.format(etime2);
		Date ctime2 = findById.getCtime();
		SimpleDateFormat simpl = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
		simpl.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String format2 = simpl.format(ctime2);
		ShopSend shopSend = new ShopSend(id2, status2, propid2, payway2, number2, price2, name2, info2, del2, format1, format2);
		SendTheRequest.delShop(5, 0, id2, shopSend);
		
		// 日志
		HttpSession session = request.getSession();
		TUser tuser = (TUser) session.getAttribute("tUser");
		OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
				"修改了ID为" + addAnaUpdate.getId() + "的商品", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
		operationLogServiceImpl.AddAnaUpdate(ot);

		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "修改成功");
		return modelMap;
	}

	@RequiresPermissions("shop.add")
	@PostMapping("/goodsAdd")
	public @ResponseBody ModelMap goodsAdd(@RequestBody Goods goods, HttpServletRequest request) {// etimeLong
		// System.out.println("g:"+goods);
		Long etimeLong = goods.getEtimeLong();
		Date etime = new Date(etimeLong);
		goods.setCtime(new Date());
		goods.setEtime(etime);
		Goods addAnaUpdate = serviceImpl.AddAnaUpdate(goods);

		Goods findById = serviceImpl.findById(addAnaUpdate.getId(), new Goods());
		// 修改的消息告知游戏
		String id2 = findById.getId();
		Integer status2 = findById.getStatus();
		Integer propid2 = findById.getPropid();
		Integer payway2 = findById.getPayway();
		Long number2 = findById.getNumber();
		Double price2 = findById.getPrice();
		String name2 = findById.getName();
		String info2 = findById.getInfo();
		Integer del2 = findById.getDel();
		Date etime2 = findById.getEtime();
		SimpleDateFormat s0 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
		s0.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String format1 = s0.format(etime2);
		Date ctime2 = findById.getCtime();
		SimpleDateFormat s = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");// 注意格式化的表达式
		s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String format2 = s.format(ctime2);
		ShopSend shopSend = new ShopSend(id2, status2, propid2, payway2, number2, price2, name2, info2, del2, format1, format2);
		SendTheRequest.delShop(5, 0, id2, shopSend);
		
		// 日志
		HttpSession session = request.getSession();
		TUser tuser = (TUser) session.getAttribute("tUser");
		OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
				"添加了ID为" + addAnaUpdate.getId() + "的商品", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
		operationLogServiceImpl.AddAnaUpdate(ot);

		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "添加成功");
		return modelMap;
	}
}
