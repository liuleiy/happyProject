package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.UserRoleDaoImpl;
import com.happyProject.admin.model.UserRole;
import com.happyProject.admin.service.UserRoleService;

@Repository
public class UserRoleServiceImpl implements UserRoleService{

	@Resource
	private UserRoleDaoImpl userRoleDaol;
	
	@Override
	public UserRole AddAnaUpdate(UserRole t) {
		return userRoleDaol.AddAnaUpdate(t);
	}

	@Override
	public void remove(UserRole t) {
		userRoleDaol.remove(t);
	}

	@Override
	public List<? extends Object> getAll(UserRole t) {
		return userRoleDaol.getAll(t);
	}

	@Override
	public int getCount(UserRole t) {
		return userRoleDaol.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, UserRole t) {
		userRoleDaol.updateById(id, map, t);
	}

	@Override
	public UserRole findById(Object id, UserRole t) {
		return userRoleDaol.findById(id, t);
	}

	@Override
	public Integer count(UserRole userRole, Map<String, Object> map) {
		return userRoleDaol.count(userRole, map);
	}

	@Override
	public List<UserRole> findByMap(Integer currentPage, Integer pageSize, Map<String, Object> map) {
		return userRoleDaol.findByMap(currentPage, pageSize, map);
	}

	@Override
	public void removeByField(String field, String value) {
		userRoleDaol.removeByField(field, value);
	}

}
