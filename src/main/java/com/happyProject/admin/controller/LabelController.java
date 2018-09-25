package com.happyProject.admin.controller;

import java.util.Date;

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
import com.happyProject.admin.model.Label;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.impl.LabelServiceImpl;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {
	@Resource
	private LabelServiceImpl labelServiceImpl;

	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;

	@RequiresPermissions("label.list")
	@PostMapping("/label")
	public @ResponseBody ModelMap goods(@RequestBody Condition condition) {
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		ModelMap modelMap = new ModelMap();
		PageBean<Label> pageBean = labelServiceImpl.findByDataAndCount(currentPage, pageSize);
		modelMap.put("data", pageBean);
		return modelMap;
	}

	@RequiresPermissions("label.del")
	@PostMapping("/labelDel")
	public @ResponseBody ModelMap labelDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				Label label = new Label();
				label.setId(id);
				labelServiceImpl.remove(label);

				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的标签", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

	//
	/*
	 * @PostMapping("/labelEdit") public @ResponseBody ModelMap
	 * labelEdit(@RequestBody Condition condition) { String idStr =
	 * condition.getIdStr(); Integer del = condition.getDel(); String[] idArray =
	 * idStr.split(","); if (idArray.length > 0) { for (String id : idArray) {
	 * Map<String, Object> m = new HashMap<String, Object>(); m.put("del", del);
	 * labelServiceImpl.updateById(id, m, new Label()); } } ModelMap modelMap = new
	 * ModelMap(); modelMap.put("message", "修改成功"); return modelMap; }
	 */
	@RequiresPermissions(value = { "label.edit","label.add" })
	@PostMapping("/updateById")
	public @ResponseBody ModelMap updateById(@RequestBody Label label, HttpServletRequest request) {
		// System.out.println("g:"+goods);
		ModelMap modelMap = new ModelMap();
		if (label.getId() != null) {

			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"修改了ID为" + label.getId() + "的标签", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);

			modelMap.put("message", "修改成功");
			labelServiceImpl.AddAnaUpdate(label);
		} else {

			modelMap.put("message", "添加成功");
			Label addAnaUpdate = labelServiceImpl.AddAnaUpdate(label);

			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"修改了ID为" + addAnaUpdate.getId() + "的标签", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);
		}

		return modelMap;
	}

	@GetMapping("/findById")
	public @ResponseBody ModelMap findById(@RequestParam(value = "id", required = false) String id) {
		Label label = labelServiceImpl.findById(id, new Label());
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", label);
		return modelMap;
	}
}
