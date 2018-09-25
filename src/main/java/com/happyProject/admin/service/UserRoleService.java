package com.happyProject.admin.service;

import java.util.List;
import java.util.Map;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.UserRole;

public interface UserRoleService extends BaseDao<UserRole>{
	Integer count(UserRole userRole, Map<String, Object> map);
	List<UserRole> findByMap(Integer currentPage, Integer pageSize,
			Map<String, Object> map);
	void removeByField(String field , String value);
}
