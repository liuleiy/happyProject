package com.happyProject.admin.controller;

import java.util.ArrayList;
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

import com.happyProject.admin.model.Condition;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.RolePerm;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.model.Trole;
import com.happyProject.admin.model.UserRole;
import com.happyProject.admin.service.impl.OperationLogServiceImpl;
import com.happyProject.admin.service.impl.RolePermServiceImpl;
import com.happyProject.admin.service.impl.SequenceServiceImpl;
import com.happyProject.admin.service.impl.TUserServiceImpl;
import com.happyProject.admin.service.impl.TpermServiceImpl;
import com.happyProject.admin.service.impl.TroleServiceImpl;
import com.happyProject.admin.service.impl.UserRoleServiceImpl;
import com.happyProject.admin.utlis.DateFormat;
import com.happyProject.admin.utlis.IpAddrs;
import com.happyProject.admin.utlis.Md5Util;
import com.happyProject.admin.utlis.TrunTime;

@RestController
@RequestMapping("/permissions")
@CrossOrigin
public class PermissionsController {

	// 部门
	@Resource
	private TroleServiceImpl troleServiceImpl;
	// 权限
	@Resource
	private TpermServiceImpl tpermServiceImpl;
	// 用户,角色
	@Resource
	private UserRoleServiceImpl userRoleServiceImpl;
	// 权限,角色
	@Resource
	private RolePermServiceImpl rolePermServiceImpl;
	// 代理,后台
	@Resource
	private TUserServiceImpl tUserServiceImpl;

	@Resource
	private SequenceServiceImpl sequenceServiceImpl;

	@Resource
	private OperationLogServiceImpl operationLogServiceImpl;

	@RequiresPermissions("role.list")
	@PostMapping("/trole")
	public @ResponseBody ModelMap trole(@RequestBody Condition condition) {

		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		PageBean<Trole> pageBean = troleServiceImpl.findByDataAndCount(currentPage, pageSize);
		List<Trole> dateMap = new ArrayList<Trole>();
		List<Trole> data = pageBean.getData();
		for (Trole trole : data) {
			Date ctime = trole.getCtime();
			Date date = DateFormat.setTime(ctime, "yyyy-MM-dd HH:mm:ss");
			trole.setCtime(date);
			dateMap.add(trole);
		}

		for (int i = 0; i < dateMap.size(); i++) {
			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("role_id", dateMap.get(i).getId());
			Integer p = userRoleServiceImpl.count(new UserRole(), smap);
			dateMap.get(i).setNumber(p);
		}
		pageBean.setData(dateMap);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}

	@RequiresPermissions("role.del")
	@PostMapping("/troleDel")
	public @ResponseBody ModelMap troleDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				Trole trole = new Trole();
				trole.setId(id);
				troleServiceImpl.remove(trole);

				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的部门角色", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);

			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

	// findById
	@GetMapping("/findById")
	public @ResponseBody ModelMap findById(@RequestParam(value = "id", required = false) String id) {
		Trole trole = troleServiceImpl.findById(id, new Trole());
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", trole);
		return modelMap;
	}

	@RequiresPermissions(value = { "role.add" , "role.edit" })
	@PostMapping("/updateById")
	public @ResponseBody ModelMap updateById(@RequestBody Trole trole, HttpServletRequest request) {
		// System.out.println("g:"+goods);
		ModelMap modelMap = new ModelMap();
		if (trole.getId() != null) {
			modelMap.put("message", "修改成功");
			troleServiceImpl.AddAnaUpdate(trole);

			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"修改了ID为" + trole.getId() + "的部门角色", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);
		} else {
			String nextId = sequenceServiceImpl.nextId("roleid");
			trole.setId(nextId);
			trole.setCtime(DateFormat.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			trole.setCtime(DateFormat.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			modelMap.put("message", "添加成功");
			sequenceServiceImpl.nextOne("roleid", nextId);
			Trole addAnaUpdate = troleServiceImpl.AddAnaUpdate(trole);

			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"添加了ID为" + addAnaUpdate.getId() + "的部门角色", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);
		}

		return modelMap;
	}

	@RequiresPermissions("tuser.list")
	@PostMapping("/tuser")
	public @ResponseBody ModelMap tuser(@RequestBody Condition condition) {
		Integer currentPage = condition.getCurrentPage();
		Integer pageSize = condition.getPageSize();
		Long startTime = condition.getStartTime();
		Long endTime = condition.getEndTime();
		PageBean<TUser> pageBean = tUserServiceImpl.findByDataAndCount(currentPage, pageSize, startTime, endTime);
		List<TUser> data = pageBean.getData();
		for (TUser tUser : data) {
			String id = tUser.getId();
			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("user_id", id);
			// 根据userid得到roleID
			List<UserRole> findByMap = userRoleServiceImpl.findByMap(-1, -1, smap);
			if (findByMap != null && findByMap.size() > 0) {
				UserRole userRole = findByMap.get(0);
				String rid = userRole.getRole_id();
				// 根据rid得到,r_name
				Trole trole = troleServiceImpl.findById(rid, new Trole());
				if (tUser != null) {
					if (trole != null) {
						tUser.setR_name(trole.getR_name());
					}
				}

			} else {
				tUser.setR_name("无");
			}
		}
		pageBean.setData(data);
		ModelMap modelMap = new ModelMap();
		modelMap.put("data", pageBean);
		return modelMap;
	}

	// tuserDel
	@RequiresPermissions("tuser.del")
	@PostMapping("/tuserDel")
	public @ResponseBody ModelMap tuserDel(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				TUser tUser = new TUser();
				tUser.setId(id);
				tUserServiceImpl.remove(tUser);

				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"删除了ID为" + id + "的后台管理", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功删除");
		return modelMap;
	}

	@RequiresPermissions(value = { "tuser.notavailable" , "tuser.available" })
	@PostMapping("/tuserEdit")
	public @ResponseBody ModelMap tuserEdit(@RequestBody Condition condition, HttpServletRequest request) {
		String idStr = condition.getIdStr();
		Integer status = condition.getStatus();
		System.out.println("tuserEdit:" + status);
		String[] idArray = idStr.split(",");
		if (idArray.length > 0) {
			for (String id : idArray) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("status", status);
				tUserServiceImpl.updateById(id, map, new TUser());

				// 日志
				HttpSession session = request.getSession();
				TUser tuser = (TUser) session.getAttribute("tUser");
				OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
						"修改了ID为" + id + "的后台管理", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				operationLogServiceImpl.AddAnaUpdate(ot);
			}
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "成功修改");
		return modelMap;
	}

	//
	@GetMapping("/findUserById")
	public @ResponseBody ModelMap findUserById(@RequestParam(value = "id", required = false) String id) {
		TUser tUser = tUserServiceImpl.findById(id, new TUser());
		// String id = tUser.getId();
		Map<String, Object> smap = new HashMap<String, Object>();
		smap.put("user_id", id);
		// 根据userid得到roleID
		List<UserRole> findByMap = userRoleServiceImpl.findByMap(-1, -1, smap);
		if (findByMap != null && findByMap.size() > 0) {
			UserRole userRole = findByMap.get(0);
			String rid = userRole.getRole_id();
			// 根据rid得到,r_name
			Trole trole = troleServiceImpl.findById(rid, new Trole());
			tUser.setR_name(trole.getR_name());
		} else {
			tUser.setR_name("无");
		}
		tUser.setPassword("");
		tUser.setPassword2("");
		// 所有部门
		List<Trole> role_list = new ArrayList<Trole>();
		List<? extends Object> all = troleServiceImpl.getAll(new Trole());
		for (Object object : all) {
			role_list.add((Trole) object);
		}
		tUser.setRole_list(role_list);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", tUser);
		return modelMap;
	}

	@GetMapping("/findRoleByAll")
	public @ResponseBody ModelMap findRoleByAll() {
		// 所有部门
		List<Trole> role_list = new ArrayList<Trole>();
		List<? extends Object> all = troleServiceImpl.getAll(new Trole());
		for (Object object : all) {
			role_list.add((Trole) object);
		}
		// tUser.setRole_list(role_list);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", role_list);
		return modelMap;
	}

	@RequiresPermissions(value = { "tuser.edit" , "tuser.add" })
	@PostMapping("/upTuserById")
	public @ResponseBody ModelMap upTuserById(@RequestBody TUser tUser, HttpServletRequest request) {
		String rid = tUser.getR_name();// 角色ID
		List<? extends Object> all = troleServiceImpl.getAll(new Trole());
		for (Object object : all) {
			Trole trole = (Trole)object;
			String r_name = trole.getR_name();
			if(rid.equals(r_name)) {
				rid = trole.getId();
			}
		}
		
		String uid = tUser.getId();// 用户ID

		ModelMap modelMap = new ModelMap();
		if (tUser.getId() != null) {
			// 先删除
			userRoleServiceImpl.removeByField("user_id", uid);
			UserRole userRole = new UserRole(uid + "." + rid, uid, rid);
			userRoleServiceImpl.AddAnaUpdate(userRole);
			modelMap.put("message", "修改成功");
			String p = tUser.getPassword();
			String password = Md5Util.getMd5String(p).toLowerCase();
			tUser.setPassword(password);
			tUserServiceImpl.AddAnaUpdate(tUser);

			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"修改了ID为" + tUser.getId() + "的后台管理", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);
		} else {
			String nextId = sequenceServiceImpl.nextId("userid");

			UserRole userRole = new UserRole(nextId + "." + rid, nextId, rid);
			userRoleServiceImpl.AddAnaUpdate(userRole);
			tUser.setId(nextId);
			tUser.setCreate_time(DateFormat.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			tUser.setUpdate_time(DateFormat.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			tUser.setStatus(0);
			modelMap.put("message", "添加成功");
			sequenceServiceImpl.nextOne("userid", nextId);
			String p = tUser.getPassword();
			String password = Md5Util.getMd5String(p).toLowerCase();
			tUser.setPassword(password);
			TUser addAnaUpdate = tUserServiceImpl.AddAnaUpdate(tUser);

			// 日志
			HttpSession session = request.getSession();
			TUser tuser = (TUser) session.getAttribute("tUser");
			OperationLog ot = new OperationLog(null, tuser.getUser_name(), IpAddrs.getIpAddr(request),
					"添加了ID为" + addAnaUpdate.getId() + "的后台管理", TrunTime.setTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			operationLogServiceImpl.AddAnaUpdate(ot);
		}

		return modelMap;
	}

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

	@GetMapping("/getPerm")
	public @ResponseBody ModelMap getPerm(@RequestParam(value = "id", required = false) String id) {
		
		Trole trole = troleServiceImpl.findById(id, new Trole());
		//获取全部的权限
		//List<? extends Object> all = tpermServiceImpl.getAll(new Tperm());
		
		
		//获得此角色的权限
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("role_id", id);
		List<RolePerm> RolePermList = rolePermServiceImpl.findByMap(-1, -1, m);
		List<String> rp = new ArrayList<String>();
		for (RolePerm rolePerm : RolePermList) {
			rp.add(rolePerm.getPerm_id());
		}
		
		/*for (int i = 0 ; i < RolePermList.size();i++ ) {
			
		}*/
		
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("trole", trole);
		//map.put("all", all);
		map.put("rp", rp);
		map.put("RolePermList", RolePermList);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", map);
		return modelMap;
	}
	
	// permSetting
	@RequiresPermissions("role.setting")
	@PostMapping("/permSetting")
	public @ResponseBody ModelMap permSetting(@RequestBody Condition condition) {
		String iid = condition.getIid();
		
		List<String> permRole = condition.getPermRole();
		
		
		//删除角色所拥有的权限
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role_id", iid);
		List<RolePerm> list = rolePermServiceImpl.findByMap(-1, -1, map);
		for (RolePerm rolePerm : list) {
			rolePermServiceImpl.remove(rolePerm);
		}
		
		//添加新的权限
		for (String string : permRole) {
			RolePerm t = new RolePerm(iid+"."+string, iid, string);
			rolePermServiceImpl.AddAnaUpdate(t);
		}
		
		
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", "修改成功");
		return modelMap;
	}
}
